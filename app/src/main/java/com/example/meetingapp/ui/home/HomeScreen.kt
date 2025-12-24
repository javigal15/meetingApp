package com.example.meetingapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meetingapp.data.fake.FakeEvents
import com.example.meetingapp.model.Event
import com.example.meetingapp.model.EventCategory
import com.example.meetingapp.ui.home.components.CategoryFilter
import com.example.meetingapp.ui.home.components.DistanceFilter
import com.example.meetingapp.ui.home.components.EventCard

@Composable
fun HomeScreen() {

    var selectedCategory by remember { mutableStateOf<EventCategory?>(null) }
    var maxDistance by remember { mutableStateOf(10f) }

    val filteredEvents = FakeEvents.events.filter { event ->
        (selectedCategory == null || event.category == selectedCategory) &&
                event.distanceKm <= maxDistance
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Eventos cerca tuyo",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        CategoryFilter(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        DistanceFilter(
            maxDistance = maxDistance,
            onDistanceChange = { maxDistance = it }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(filteredEvents) { event ->
                EventCard(event)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}




