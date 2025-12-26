package com.example.meetingapp.data.fake

import com.example.meetingapp.R
import com.example.meetingapp.model.User

object FakeUser {

    val user = User(
        id = "u1",
        name = "Javi",
        age = 30,
        bio = "Ingeniero, me gustan los mates, la tecnología y conocer gente copada.",
        interests = listOf("Mate", "Programación", "Deporte", "Viajes"),
        photos = listOf(
            R.drawable.profile_1,
            R.drawable.profile_2,
            R.drawable.profile_3
        )
    )
}
