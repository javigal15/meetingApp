package com.example.meetingapp.mainscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel
import com.example.meetingapp.mainscreen.navgraph.NavGraph
import com.example.meetingapp.mainscreen.scaffold.BottomBar
import com.example.meetingapp.navigation.Screen

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val meetingsViewModel: MeetingsViewModel = viewModel()
    val myEvents by meetingsViewModel.myEvents.collectAsState()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = currentRoute,
                badgeCount = myEvents.size
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.CreateEvent.route)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear evento")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        NavGraph(
            navController = navController,
            meetingsViewModel = meetingsViewModel,
            modifier = Modifier.padding(padding)
        )
    }
}

