package com.example.meetingapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CreateEvent : Screen("create_event")
    object Profile : Screen("profile")
}
