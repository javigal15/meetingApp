package com.example.meetingapp.model

data class ChatMessage(
    val sender: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)