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
    maxDistance: Int,
    onDistanceChange: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) { // solo horizontal
        Text("Distancia máxima: $maxDistance km")
        Slider(
            value = maxDistance.toFloat(),
            onValueChange = { onDistanceChange(it.toInt()) },
            valueRange = 1f..20f,
            steps = 19
        )
    }
}
