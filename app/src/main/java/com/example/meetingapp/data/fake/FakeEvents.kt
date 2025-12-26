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
            creator = "Lucía",
            isPublic = true
        ),
        Event(
            id = "2",
            title = "Birras después del laburo",
            description = "Happy hour",
            category = "Bar",
            distanceKm = 5,
            creator = "Marcos",
            isPublic = true
        )
    )
}
