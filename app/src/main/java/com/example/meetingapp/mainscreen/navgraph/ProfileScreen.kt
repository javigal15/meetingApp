package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meetingapp.data.fake.FakeUser
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel
import com.example.meetingapp.ui.profile.components.PhotoCarousel
import com.example.meetingapp.ui.profile.components.ProfileHeader
import com.example.meetingapp.ui.profile.components.UserEventsSection

@Composable
fun ProfileScreen(
    viewModel: MeetingsViewModel
) {
    val myEvents by viewModel.myEvents.collectAsState()
    val user = FakeUser.user

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {

        item {
            ProfileHeader(user)
        }

        if (user.photos.isNotEmpty()) {
            item {
                PhotoCarousel(user.photos)
            }
        }

        item {
            UserEventsSection(
                events = myEvents,
                onRemove = { event ->
                    viewModel.removeEvent(event)
                }
            )
        }
    }
}
