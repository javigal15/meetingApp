package com.example.meetingapp.ui.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DistanceFilter(
    maxDistance: Float,
    onDistanceChange: (Float) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Distancia máxima: ${maxDistance.toInt()} km")
        Slider(
            value = maxDistance,
            onValueChange = onDistanceChange,
            valueRange = 1f..20f
        )
    }
}
