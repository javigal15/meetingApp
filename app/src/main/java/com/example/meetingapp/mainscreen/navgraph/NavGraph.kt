package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
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
            HomeScreen(meetingsViewModel)
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
            ProfileScreen(meetingsViewModel)
        }
    }
}


