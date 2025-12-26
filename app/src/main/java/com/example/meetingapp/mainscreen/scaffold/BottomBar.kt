package com.example.meetingapp.mainscreen.scaffold

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel
import com.example.meetingapp.navigation.Screen

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?,
    badgeCount: Int
) {
    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            icon = {
                Icon(Icons.Default.Home, contentDescription = "Inicio")
            },
            label = { Text("Inicio") }
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = {
                navController.navigate(Screen.Profile.route)
            },
            icon = {
                BadgedBox(
                    badge = {
                        if (badgeCount > 0) {
                            Badge { Text(badgeCount.toString()) }
                        }
                    }
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Perfil")
                }
            },
            label = { Text("Perfil") }
        )
    }
}

