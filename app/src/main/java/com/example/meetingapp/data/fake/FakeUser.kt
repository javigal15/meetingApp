package com.example.meetingapp.data.fake

import com.example.meetingapp.R
import com.example.meetingapp.model.User

object FakeUsers {

    private val users = mutableMapOf<String, User>()

    fun ensureUserExists(
        id: String,
        name: String
    ) {
        if (users.containsKey(id)) return

        users[id] = User(
            id = id,
            name = name,
            age = 30,
            bio = "Nuevo en la app 👋",
            interests = emptyList(),
            photos = emptyList(),
            city = "Buenos Aires",
            country = "Argentina"
        )
    }

    fun getById(id: String): User? = users[id]
}