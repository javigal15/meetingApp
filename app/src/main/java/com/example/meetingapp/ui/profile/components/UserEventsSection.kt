package com.example.meetingapp.ui.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meetingapp.model.Event

@Composable
fun UserEventsSection(
    events: List<Event>,
    onRemove: (Event) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = "Mis eventos",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (events.isEmpty()) {
            Text("Todavía no aceptaste eventos")
        }

        events.forEach { event ->
            UserEventCard(
                event = event,
                onRemove = { onRemove(event) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


