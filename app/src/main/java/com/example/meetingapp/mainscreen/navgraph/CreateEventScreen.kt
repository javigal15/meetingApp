package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel

@Composable
fun CreateEventScreen(
    navController: NavHostController,
    meetingsViewModel: MeetingsViewModel,
    onEventCreated: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Mate") }
    var distance by remember { mutableStateOf(1) }
    var isPublic by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("Crear evento", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Categoría")
        val categories = listOf("Mate", "Bar", "Juegos", "Deporte", "Comida")
        categories.forEach { category ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category }
                )
                Text(category)
            }
        }

        Text("Distancia: $distance km")
        Slider(
            value = distance.toFloat(),
            onValueChange = { distance = it.toInt() },
            valueRange = 1f..20f,
            steps = 19
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isPublic,
                onCheckedChange = { isPublic = it }
            )
            Text("Evento público")
        }

        Button(
            onClick = {
                meetingsViewModel.createEvent(
                    title = title,
                    description = description,
                    category = selectedCategory,
                    distanceKm = distance,
                    isPublic = isPublic
                )
                // Limpiar campos opcionalmente
                title = ""
                description = ""
                selectedCategory = "Mate"
                distance = 1
                isPublic = true

                onEventCreated()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotBlank()
        ) {
            Text("Crear evento")
        }
    }
}
