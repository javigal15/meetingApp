package com.example.meetingapp.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.meetingapp.model.User
import com.example.meetingapp.model.Event
import com.example.meetingapp.R
import com.example.meetingapp.data.fake.FakeEvents

class ProfileViewModel : ViewModel() {

    private val _user = MutableStateFlow(
        User(
            id = "1",
            name = "Javi",
            age = 30,
            bio = "Ingeniero, tranquilo y sociable",
            interests = listOf("Mate", "Deporte", "Tecnología","Juegos de mesa"),
            photos = listOf(
                R.drawable.profile_1,
                R.drawable.profile_2,
                R.drawable.profile_3
            )
        )
    )
    val user: StateFlow<User> = _user

    private val _events = MutableStateFlow(
        FakeEvents.events.filter { it.hostName == "Javi" }
    )
    val events: StateFlow<List<Event>> = _events
}
