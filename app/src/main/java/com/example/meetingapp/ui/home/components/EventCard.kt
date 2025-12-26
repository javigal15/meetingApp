package com.example.meetingapp.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meetingapp.model.Event

@Composable
fun EventCard(
    event: Event,
    onAccept: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onAccept) {
                Text("Aceptar meeting")
            }
        }
    }
}

