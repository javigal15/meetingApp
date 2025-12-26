package com.example.meetingapp.data.fake

import com.example.meetingapp.model.Event

object FakeEvents {

    val events = listOf(
        Event(
            id = "1",
            title = "Mate en la plaza",
            description = "Tranqui para charlar",
            category = "Mate",
            distanceKm = 2,
            isPublic = true,
            creatorId = "user_lucia",
            creatorName = "Lucía",
            participants = listOf("user_lucia")
        ),
        Event(
            id = "2",
            title = "Birras después del laburo",
            description = "Happy hour",
            category = "Bar",
            distanceKm = 5,
            isPublic = true,
            creatorId = "user_marcos",
            creatorName = "Marcos",
            participants = listOf("user_marcos")
        )
    )
}
