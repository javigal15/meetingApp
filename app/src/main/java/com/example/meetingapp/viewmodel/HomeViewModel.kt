package com.example.meetingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.meetingapp.model.Event
import com.example.meetingapp.model.EventCategory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {

    private val allEvents = listOf(
        Event("1", "Mate en la plaza", "Tranqui para charlar", EventCategory.MATE, 1.2, "Lucía", true),
        Event("2", "Partido de tenis", "Singles", EventCategory.DEPORTE, 3.5, "Martín", true),
        Event("3", "Juegos de mesa", "Catan y TEG", EventCategory.JUEGOS, 2.0, "Sofi", true),
        Event("4", "After office", "Birras y charla", EventCategory.BAR, 4.8, "Tomás", true)
    )

    private val _selectedCategory = MutableStateFlow<EventCategory?>(null)
    private val _maxDistance = MutableStateFlow(10.0)

    val events: StateFlow<List<Event>> =
        combine(_selectedCategory, _maxDistance) { category, maxDistance ->
            allEvents.filter { event ->
                (category == null || event.category == category) &&
                        event.distanceKm <= maxDistance
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            allEvents
        )

    fun filterByCategory(category: EventCategory?) {
        _selectedCategory.value = category
    }

    fun setMaxDistance(distance: Double) {
        _maxDistance.value = distance
    }
}
