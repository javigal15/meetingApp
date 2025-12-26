package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.meetingapp.mainscreen.meetingviewmodel.ChatViewModel
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel
import com.example.meetingapp.navigation.Screen

@Composable
fun NavGraph(
    navController: NavHostController,
    meetingsViewModel: MeetingsViewModel,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(meetingsViewModel, navController)
        }

        composable(Screen.CreateEvent.route) {
            CreateEventScreen(
                meetingsViewModel = meetingsViewModel,
                onEventCreated = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(meetingsViewModel, navController)
        }

        // Composable del chat
        composable("chat/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: return@composable
            val chatViewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

            // Buscar primero en myEvents (tu perfil)
            val myEvents by meetingsViewModel.myEvents.collectAsState()
            val allEvents by meetingsViewModel.filteredEvents.collectAsState()
            val event = myEvents.find { it.id == eventId } ?: allEvents.find { it.id == eventId }

            if (event != null) {
                EventChatScreen(
                    event = event,
                    currentUser = "Javi",
                    chatViewModel = chatViewModel
                )
            } else {
                Text("Evento no encontrado") // fallback visual para debug
            }
        }
    }
}




