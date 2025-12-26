package com.example.meetingapp.data.fake

import com.example.meetingapp.model.Event
import com.example.meetingapp.model.EventCategory

object FakeEvents {

    val events = listOf(
        Event(
            title = "Mate en la plaza",
            description = "Tranqui para charlar y tomar unos mates",
            category = "Mate",
            distanceKm = 2,
            isPublic = true,
            creator = "Lucía"
        ),
        Event(
            title = "Birras después del laburo",
            description = "Happy hour para cortar la semana",
            category = "Bar",
            distanceKm = 5,
            isPublic = true,
            creator = "Marcos"
        ),
        Event(
            title = "Juegos de mesa",
            description = "Catan, TEG y cartas",
            category = "Juegos",
            distanceKm = 3,
            isPublic = false,
            creator = "Sofi"
        ),
        Event(
            title = "Running nocturno",
            description = "5K ritmo tranquilo",
            category = "Deporte",
            distanceKm = 1,
            isPublic = true,
            creator = "Nico"
        ),
        Event(
            title = "Cena casera",
            description = "Pasta hecha en casa, pocos lugares",
            category = "Comida",
            distanceKm = 4,
            isPublic = false,
            creator = "Ana"
        )
    )
}
