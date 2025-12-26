package com.example.meetingapp.mainscreen.meetingviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetingapp.data.fake.FakeEvents
import com.example.meetingapp.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MeetingsViewModel : ViewModel() {

    private val _allEvents = MutableStateFlow(FakeEvents.events)

    private val _myEvents = MutableStateFlow<List<Event>>(emptyList())
    val myEvents: StateFlow<List<Event>> = _myEvents

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> = _uiMessage

    // 👇 category como String (igual que Event)
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    // 👇 distancia como Int
    private val _maxDistanceKm = MutableStateFlow(10)
    val maxDistanceKm: StateFlow<Int> = _maxDistanceKm

    // 👇 ESTADO DERIVADO
    val filteredEvents: StateFlow<List<Event>> =
        combine(
            _allEvents,
            _selectedCategory,
            _maxDistanceKm,
            _myEvents
        ) { events, category, maxDistance, myEvents ->

            events.filter { event ->
                (category == null || event.category == category) &&
                        event.distanceKm <= maxDistance &&
                        event !in myEvents
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            FakeEvents.events
        )

    fun acceptEvent(event: Event) {
        if (event in _myEvents.value) return

        _myEvents.value = _myEvents.value + event
        _uiMessage.value = "Evento agregado a tu perfil"
    }

    fun removeEvent(event: Event) {
        _myEvents.value = _myEvents.value - event
        _uiMessage.value = "Evento eliminado"
    }

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun setMaxDistance(distance: Int) {
        _maxDistanceKm.value = distance
    }

    fun clearMessage() {
        _uiMessage.value = null
    }

    fun createEvent(
        title: String,
        description: String,
        category: String,
        distanceKm: Int,
        isPublic: Boolean
    ) {
        val newEvent = Event(
            title = title,
            description = description,
            category = category,
            distanceKm = distanceKm,
            isPublic = isPublic,
            creator = "Vos"
        )

        _myEvents.value = _myEvents.value + newEvent
        _uiMessage.value = "Evento creado: $title"
    }
}
