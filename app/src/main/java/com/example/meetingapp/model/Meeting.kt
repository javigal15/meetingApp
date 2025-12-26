package com.example.meetingapp.model

data class Meeting(
    val id: String,
    val title: String,
    val description: String,
    val hostId: String,
    val hostName: String,
    val category: EventCategory,
    val distanceKm: Double,
    val status: MeetingStatus
)
