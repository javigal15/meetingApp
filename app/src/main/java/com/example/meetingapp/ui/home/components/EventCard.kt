package com.example.meetingapp.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.meetingapp.model.Event

@Composable
fun EventCard(
    event: Event,
    currentUser: String,
    onAccept: (() -> Unit)? = null,
    navController: androidx.navigation.NavController
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = event.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text(event.category) })
                AssistChip(onClick = {}, label = { Text("${event.distanceKm} km") })
                AssistChip(onClick = {}, label = { Text(if (event.isPublic) "Público" else "Privado") })
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Creado por ${event.creator}",
                    style = MaterialTheme.typography.bodySmall
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Mostrar "Unirme" solo si no estoy en participants
                    if (currentUser !in event.participants) {
                        onAccept?.let {
                            Button(onClick = it) { Text("Unirme") }
                        }
                    }

                    // Mostrar Chat solo si estoy en participants
                    if (currentUser in event.participants) {
                        Button(onClick = { navController.navigate("chat/${event.id}") }) {
                            Text("Chat")
                        }
                    }
                }
            }
        }
    }
}




