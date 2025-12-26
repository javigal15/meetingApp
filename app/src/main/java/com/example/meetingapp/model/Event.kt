package com.example.meetingapp.model

data class Event(
    val title: String,
    val description: String,
    val category: String,
    val distanceKm: Int,
    val isPublic: Boolean,
    val creator: String
)