package com.example.meetingapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }

    object Chat : Screen("chat/{eventId}") {
        fun createRoute(eventId: String) = "chat/$eventId"
    }

    object CreateEvent : Screen("create_event")
}