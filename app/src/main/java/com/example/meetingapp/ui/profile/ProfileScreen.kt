package com.example.meetingapp.ui.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meetingapp.ui.profile.components.PhotoCarousel
import com.example.meetingapp.ui.profile.components.ProfileHeader
import com.example.meetingapp.ui.profile.components.UserEventsSection

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val user by viewModel.user.collectAsState()
    val events by viewModel.events.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item { ProfileHeader(user) }
        item { PhotoCarousel(user.photos) }
        item { UserEventsSection(events) }
    }
}

