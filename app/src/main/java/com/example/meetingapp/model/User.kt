package com.example.meetingapp.model

data class User(
    val id: String,
    val name: String,
    val age: Int,
    val bio: String,
    val interests: List<String>,
    val photos: List<Int> // drawable ids por ahora
)
