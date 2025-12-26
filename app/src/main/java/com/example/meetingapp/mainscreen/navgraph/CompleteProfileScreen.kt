package com.example.meetingapp.mainscreen.navgraph

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.meetingapp.data.repository.UserRepository
import com.example.meetingapp.model.UserEntity
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun CompleteProfileScreen(
    userId: String,
    email: String,
    onProfileCompleted: (UserEntity) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var interests by remember { mutableStateOf(listOf<String>()) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) photoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Completa tu perfil", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellido") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Edad") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("Ciudad") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = country, onValueChange = { country = it }, label = { Text("País") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = bio, onValueChange = { bio = it }, label = { Text("Bio") }, modifier = Modifier.fillMaxWidth())

        Button(onClick = { launcher.launch("image/*") }) { Text("Seleccionar foto") }

        photoUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Foto seleccionada",
                modifier = Modifier.size(150.dp).padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                scope.launch {
                    // 1️⃣ Subir foto a Firebase Storage
                    val photoUrls = if (photoUri != null) {
                        val storageRef = com.google.firebase.storage.FirebaseStorage.getInstance().reference
                        val photoRef = storageRef.child("users/$userId/profile.jpg")
                        photoRef.putFile(photoUri!!).await()
                        listOf(photoRef.downloadUrl.await().toString())
                    } else emptyList()

                    // 2️⃣ Crear usuario
                    val user = UserEntity(
                        id = userId,
                        name = name,
                        lastName = lastName,
                        age = age.toIntOrNull() ?: 0,
                        bio = bio,
                        interests = interests,
                        photos = photoUrls,
                        city = city,
                        country = country
                    )

                    // 3️⃣ Guardar en Firestore
                    UserRepository.setUser(user)

                    // 4️⃣ Notificar a AppRoot
                    onProfileCompleted(user)
                }
            },
            enabled = name.isNotBlank() && lastName.isNotBlank() && age.isNotBlank() && photoUri != null
        ) {
            Text("Guardar perfil")
        }
    }
}
