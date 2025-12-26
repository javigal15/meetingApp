package com.example.meetingapp.mainscreen.network

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SessionManager {
    var currentUserId: String? = null
    var isUserCreated by mutableStateOf(false)
}
