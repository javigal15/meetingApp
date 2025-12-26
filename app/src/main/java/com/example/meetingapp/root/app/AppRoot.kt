// AppRoot.kt
package com.example.meetingapp.root.app

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.auth.FirebaseAuth
import com.example.meetingapp.mainscreen.navgraph.NavGraph
import com.example.meetingapp.mainscreen.navgraph.CompleteProfileScreen
import com.example.meetingapp.mainscreen.scaffold.BottomBar
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel
import com.example.meetingapp.data.repository.UserRepository
import com.example.meetingapp.mainscreen.network.SessionManager
import com.example.meetingapp.model.UserEntity
import com.example.meetingapp.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun AppRoot(
    navController: NavHostController,
    meetingsViewModel: MeetingsViewModel,
    launchGoogleSignIn: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var currentUser by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }
    var firestoreUser by remember { mutableStateOf<UserEntity?>(null) }
    var loading by remember { mutableStateOf(true) }

    // Escucha de sesión Firebase
    DisposableEffect(Unit) {
        val auth = FirebaseAuth.getInstance()
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            currentUser = firebaseAuth.currentUser
        }
        auth.addAuthStateListener(listener)
        onDispose { auth.removeAuthStateListener(listener) }
    }

    // 🔥 Cuando cambia currentUser, cargar perfil y eventos
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            loading = true
            // 1️⃣ Obtener usuario
            firestoreUser = UserRepository.getUserById(currentUser!!.uid)

            // 2️⃣ Si existe usuario, cargar eventos
            firestoreUser?.let { user ->
                SessionManager.currentUserId = user.id
                meetingsViewModel.loadMyEvents(user.id) // debe implementarse
                meetingsViewModel.loadAllEvents()       // opcional
            }

            loading = false
        }
    }

    if (currentUser == null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Inicia sesión para continuar")
            Spacer(Modifier.height(16.dp))
            Button(onClick = { launchGoogleSignIn() }) { Text("Iniciar sesión con Google") }
        }
        return
    }

    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Si el usuario no tiene perfil
    if (firestoreUser == null) {
        CompleteProfileScreen(
            userId = currentUser!!.uid,
            email = currentUser!!.email ?: "",
            onProfileCompleted = { user ->
                firestoreUser = user
                SessionManager.currentUserId = user.id
            }
        )
        return
    }

    // Perfil completo → mostrar app normal
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val badgeCount by meetingsViewModel.myEvents.collectAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.CreateEvent.route) }) {
                Icon(Icons.Default.Add, contentDescription = "Crear evento")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = currentRoute,
                badgeCount = badgeCount.size,
                onLogout = { FirebaseAuth.getInstance().signOut() }
            )
        }
    ) { padding ->
        NavGraph(navController, meetingsViewModel, Modifier.padding(padding))
    }
}

