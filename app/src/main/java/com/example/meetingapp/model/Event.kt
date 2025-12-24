package com.example.meetingapp.model

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val category: EventCategory,
    val distanceKm: Double,
    val hostName: String,
    val isPublic: Boolean = true
)