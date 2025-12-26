package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meetingapp.data.repository.UserRepository
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel
import com.example.meetingapp.mainscreen.network.SessionManager
import com.example.meetingapp.ui.profile.components.PhotoCarousel
import com.example.meetingapp.ui.profile.components.UserEventCard
import com.example.meetingapp.model.UserEntity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: String,
    viewModel: MeetingsViewModel,
    navController: NavController
) {
    val myEvents by viewModel.myEvents.collectAsState()
    val allEvents by viewModel.filteredEvents.collectAsState()

    val isMyProfile = userId == SessionManager.currentUserId

    var user by remember { mutableStateOf<UserEntity?>(null) }
    var loading by remember { mutableStateOf(true) }

    // 🔹 Cargar usuario desde Firestore
    LaunchedEffect(userId) {
        loading = true
        user = try {
            UserRepository.getUserById(userId)
        } catch (e: Exception) {
            null
        }
        loading = false
    }

    // ⏳ Loading
    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // ❌ Usuario no encontrado
    if (user == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Usuario no encontrado")
        }
        return
    }

    val profileEvents = if (isMyProfile) {
        myEvents
    } else {
        allEvents.filter { it.creatorId == userId }
    }

    // BottomSheet para fotos
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showPhotos by remember { mutableStateOf(false) }

    if (showPhotos) {
        ModalBottomSheet(
            onDismissRequest = { showPhotos = false },
            sheetState = sheetState
        ) {
            PhotoCarousel(user!!.photos)
        }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val photoSize = screenHeight / 5

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // ---------- HEADER ----------
        item {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (user!!.photos.isNotEmpty()) {
                    AsyncImage(
                        model = user!!.photos.first(),
                        contentDescription = "Foto",
                        modifier = Modifier
                            .size(photoSize)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(user!!.name + " " + (user!!.lastName ?: ""),
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "${user!!.city}, ${user!!.country}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = user!!.bio,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(8.dp))

                if (user!!.interests.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        user!!.interests.forEach {
                            AssistChip(onClick = {}, label = { Text(it) })
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Button(onClick = { showPhotos = true }) {
                    Text("Fotos")
                }
            }
        }

        // ---------- EVENTOS ----------
        if (profileEvents.isNotEmpty()) {

            item {
                Text(
                    text = if (isMyProfile) "Mis eventos" else "Eventos creados",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(profileEvents) { event ->
                UserEventCard(
                    event = event,
                    navController = navController,
                    showRemove = isMyProfile,
                    onRemove = {
                        if (isMyProfile) viewModel.removeEvent(event)
                    }
                )
            }
        }
    }
}
