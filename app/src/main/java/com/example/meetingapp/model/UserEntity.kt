package com.example.meetingapp.model

data class UserEntity(
    val id: String = "",
    val name: String = "",
    val lastName: String = "",
    val age: Int = 0,
    val bio: String = "",
    val interests: List<String> = emptyList(),
    val photos: List<String> = emptyList(), // URLs
    val city: String = "",
    val country: String = ""
)

