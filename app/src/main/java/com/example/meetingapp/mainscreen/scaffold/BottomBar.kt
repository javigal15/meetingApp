// BottomBar.kt
package com.example.meetingapp.mainscreen.scaffold

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.navigation.NavController
import com.example.meetingapp.mainscreen.network.SessionManager
import com.example.meetingapp.navigation.Screen

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?,
    badgeCount: Int,
    onLogout: (() -> Unit)? = null
) {
    val currentUserId = SessionManager.currentUserId

    NavigationBar {

        // HOME
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Inicio") }
        )

        // PROFILE
        NavigationBarItem(
            selected = currentRoute?.startsWith("profile") == true,
            onClick = {
                currentUserId?.let {
                    navController.navigate(Screen.Profile.createRoute(it)) {
                        launchSingleTop = true
                    }
                }
            },
            enabled = currentUserId != null,
            icon = {
                BadgedBox(
                    badge = {
                        if (badgeCount > 0) {
                            Badge { Text(badgeCount.toString()) }
                        }
                    }
                ) {
                    Icon(Icons.Default.Person, null)
                }
            },
            label = { Text("Perfil") }
        )

        // LOGOUT
        IconButton(onClick = { onLogout?.invoke() }) {
            Icon(Icons.Default.Close, contentDescription = "Cerrar sesión")
        }
    }
}
