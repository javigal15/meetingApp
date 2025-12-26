package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.meetingapp.mainscreen.meetingviewmodel.ChatViewModel
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel
import com.example.meetingapp.mainscreen.network.SessionManager
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

        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId == null) {
                Text("Error: userId no recibido")
                return@composable
            }

            ProfileScreen(
                userId = userId,
                viewModel = meetingsViewModel,
                navController = navController
            )
        }

        composable(
            route = Screen.Chat.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            if (eventId == null) {
                Text("Error: eventId no recibido")
                return@composable
            }

            val chatViewModel: ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

            val myEvents by meetingsViewModel.myEvents.collectAsState()
            val allEvents by meetingsViewModel.filteredEvents.collectAsState()

            val event = myEvents.find { it.id == eventId }
                ?: allEvents.find { it.id == eventId }

            if (event != null) {
                EventChatScreen(
                    event = event,
                    currentUser = SessionManager.currentUserId ?: "",
                    chatViewModel = chatViewModel
                )
            } else {
                Text("Evento no encontrado")
            }
        }

        composable(Screen.CreateEvent.route) {
            CreateEventScreen(
                navController = navController,
                meetingsViewModel = meetingsViewModel,
                onEventCreated = {
                    navController.popBackStack()
                }
            )
        }

    }
}





