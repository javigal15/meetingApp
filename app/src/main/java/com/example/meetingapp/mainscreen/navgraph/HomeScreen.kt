package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meetingapp.ui.home.components.CategoryFilter
import com.example.meetingapp.ui.home.components.DistanceFilter
import com.example.meetingapp.ui.home.components.EventCard
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel

@Composable
fun HomeScreen(
    viewModel: MeetingsViewModel
) {
    val events by viewModel.filteredEvents.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val message by viewModel.uiMessage.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val maxDistance by viewModel.maxDistanceKm.collectAsState()

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {

            Text(
                text = "Eventos cerca tuyo",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )

            CategoryFilter(
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.setCategory(it) }
            )

            DistanceFilter(
                maxDistance = maxDistance,
                onDistanceChange = { viewModel.setMaxDistance(it) }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(events) { event ->
                    EventCard(
                        event = event,
                        onAccept = {
                            viewModel.acceptEvent(event)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}




