package com.example.meetingapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.meetingapp.data.fake.FakeEvents
import com.example.meetingapp.model.Event
import com.example.meetingapp.model.EventCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _selectedCategory = MutableStateFlow<EventCategory?>(null)
    private val _events = MutableStateFlow(FakeEvents.events)

    val events: StateFlow<List<Event>> = _events
    val selectedCategory: StateFlow<EventCategory?> = _selectedCategory

    fun onCategorySelected(category: EventCategory?) {
        _selectedCategory.value = category
        _events.value =
            if (category == null) FakeEvents.events
            else FakeEvents.events.filter { it.category == category }
    }
}