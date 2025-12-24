package com.example.meetingapp.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meetingapp.model.EventCategory
import com.example.meetingapp.ui.home.components.CategoryChip
import com.example.meetingapp.ui.home.components.EventCard

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val events by viewModel.events.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(
            text = "Eventos cerca tuyo",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow {
            item {
                CategoryChip(
                    text = "Todos",
                    selected = selectedCategory == null,
                    onClick = { viewModel.onCategorySelected(null) }
                )
            }

            items(EventCategory.values()) { category ->
                CategoryChip(
                    text = category.label,
                    selected = selectedCategory == category,
                    onClick = { viewModel.onCategorySelected(category) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(events) { event ->
                EventCard(event)
            }
        }
    }
}