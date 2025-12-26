package com.example.meetingapp.mainscreen.scaffold

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavController
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

