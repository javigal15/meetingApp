package com.example.meetingapp.model

data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val distanceKm: Int = 0,
    val isPublic: Boolean = true,
    val creatorId: String = "",
    val creatorName: String = "",
    val participants: List<String> = emptyList()
)
