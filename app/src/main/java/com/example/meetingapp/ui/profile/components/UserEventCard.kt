package com.example.meetingapp.ui.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meetingapp.model.Event
import com.example.meetingapp.navigation.Screen

@Composable
fun UserEventCard(
    event: Event,
    onRemove: () -> Unit,
    navController: NavController,
    showRemove: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Título y descripción
            Text(event.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(event.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Creado por ${event.creatorName}",
                modifier = Modifier.clickable {
                    navController.navigate(
                        Screen.Profile.createRoute(event.creatorId)
                    )
                }
            )

            // Información extra
            Text(
                text = "Categoría: ${event.category} | Distancia: ${event.distanceKm} km",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Creador: ${event.creatorName} | Público: ${if (event.isPublic) "Sí" else "No"}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onRemove) {
                    Text("Eliminar")
                }

                Button(onClick = {
                    navController.navigate("chat/${event.id}")
                }) {
                    Text("Chat")
                }
            }
        }
    }
}
