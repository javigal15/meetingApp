package com.example.meetingapp.mainscreen.meetingviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetingapp.mainscreen.network.SessionManager
import com.example.meetingapp.model.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MeetingsViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true) // persistencia offline
            .build()
    }

    private val _allEvents = MutableStateFlow<List<Event>>(emptyList())
    val allEvents: StateFlow<List<Event>> = _allEvents

    private val _myEvents = MutableStateFlow<List<Event>>(emptyList())
    val myEvents: StateFlow<List<Event>> = _myEvents

    private val _uiMessage = MutableStateFlow<String?>(null)
    val uiMessage: StateFlow<String?> = _uiMessage

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    private val _maxDistanceKm = MutableStateFlow(10)
    val maxDistanceKm: StateFlow<Int> = _maxDistanceKm

    val filteredEvents: StateFlow<List<Event>> =
        combine(_allEvents, _selectedCategory, _maxDistanceKm, _myEvents) { events, category, maxDistance, myEvents ->
            events.filter { event ->
                (category == null || event.category == category) &&
                        event.distanceKm <= maxDistance &&
                        (event.isPublic || event in myEvents)
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        startListeningEvents()
    }

    /** ---------------- Carga de eventos ---------------- **/

    // Listener en tiempo real
    private fun startListeningEvents() {
        firestore.collection("events").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e("MeetingsViewModel", "Error al obtener eventos", e)
                return@addSnapshotListener
            }
            val events = snapshot?.toObjects(Event::class.java) ?: emptyList()
            _allEvents.value = events
            val userId = SessionManager.currentUserId
            _myEvents.value = if (userId != null) events.filter { it.participants.contains(userId) } else emptyList()
        }
    }

    // Carga explícita de todos los eventos
    fun loadAllEvents() {
        firestore.collection("events")
            .get()
            .addOnSuccessListener { snapshot ->
                _allEvents.value = snapshot.toObjects(Event::class.java)
            }
            .addOnFailureListener { e -> Log.e("MeetingsViewModel", "Error cargando eventos", e) }
    }

    // Carga explícita de los eventos del usuario
    fun loadMyEvents(userId: String) {
        firestore.collection("events")
            .whereArrayContains("participants", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                _myEvents.value = snapshot.toObjects(Event::class.java)
            }
            .addOnFailureListener { e -> Log.e("MeetingsViewModel", "Error cargando mis eventos", e) }
    }

    /** ---------------- Gestión de eventos ---------------- **/

    fun acceptEvent(event: Event) {
        val userId = SessionManager.currentUserId ?: return
        if (event in _myEvents.value) return

        val updatedParticipants = event.participants + userId

        firestore.collection("events").document(event.id)
            .update("participants", updatedParticipants)
            .addOnSuccessListener {
                _myEvents.value = _myEvents.value + event.copy(participants = updatedParticipants)
                _allEvents.value = _allEvents.value.map {
                    if (it.id == event.id) it.copy(participants = updatedParticipants) else it
                }
                _uiMessage.value = "Evento agregado a tu perfil"
            }
            .addOnFailureListener { e -> Log.e("MeetingsViewModel", "No se pudo agregar participante", e) }
    }

    fun removeEvent(event: Event) {
        val userId = SessionManager.currentUserId ?: return
        val updatedParticipants = event.participants - userId

        firestore.collection("events").document(event.id)
            .update("participants", updatedParticipants)
            .addOnSuccessListener {
                _myEvents.value = _myEvents.value - event
                _allEvents.value = _allEvents.value.map {
                    if (it.id == event.id) it.copy(participants = updatedParticipants) else it
                }
                _uiMessage.value = "Evento eliminado"
            }
            .addOnFailureListener { e -> Log.e("MeetingsViewModel", "No se pudo eliminar participante", e) }
    }

    fun createEvent(
        title: String,
        description: String,
        category: String,
        distanceKm: Int,
        isPublic: Boolean
    ) {
        val creatorId = SessionManager.currentUserId ?: return
        val newEvent = Event(
            id = System.currentTimeMillis().toString(),
            title = title,
            description = description,
            category = category,
            distanceKm = distanceKm,
            isPublic = isPublic,
            creatorId = creatorId,
            creatorName = creatorId,
            participants = listOf(creatorId)
        )

        firestore.collection("events").document(newEvent.id)
            .set(newEvent)
            .addOnSuccessListener {
                _allEvents.value = _allEvents.value + newEvent
                _myEvents.value = _myEvents.value + newEvent
                _uiMessage.value = "Evento creado: $title"
            }
            .addOnFailureListener { e -> Log.e("MeetingsViewModel", "No se pudo crear evento", e) }
    }

    /** ---------------- UI ---------------- **/

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun setMaxDistance(distance: Int) {
        _maxDistanceKm.value = distance
    }

    fun clearMessage() {
        _uiMessage.value = null
    }
}

