package com.example.meetingapp.data.fake

import com.example.meetingapp.model.Event
import com.example.meetingapp.model.EventCategory

object FakeEvents {

    val events = listOf(
        Event(
            id = "1",
            title = "Mate en la plaza",
            description = "Tranqui para charlar",
            category = EventCategory.MATE,
            distanceKm = 2,
            hostName = "Lucía"
        ),
        Event(
            id = "2",
            title = "Birras después del laburo",
            description = "Happy hour",
            category = EventCategory.BAR,
            distanceKm = 5,
            hostName = "Marcos"
        ),
        Event(
            id = "3",
            title = "Juegos de mesa",
            description = "Catan, TEG, etc",
            category = EventCategory.JUEGOS,
            distanceKm = 3,
            hostName = "Sofi"
        )
    )
}