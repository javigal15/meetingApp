package com.example.meetingapp.mainscreen.meetingviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meetingapp.model.ChatMessage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _chats = MutableStateFlow<Map<String, List<ChatMessage>>>(emptyMap())
    val chats: StateFlow<Map<String, List<ChatMessage>>> = _chats

    private val listeners = mutableMapOf<String, ListenerRegistration>()

    fun subscribeToEvent(eventId: String) {
        if (listeners.containsKey(eventId)) return

        val listener = db.collection("chats")
            .document(eventId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val messages = it.documents.map { doc ->
                        ChatMessage(
                            sender = doc.getString("sender") ?: "",
                            content = doc.getString("content") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0L
                        )
                    }
                    _chats.value = _chats.value + (eventId to messages)
                }
            }
        listeners[eventId] = listener
    }

    fun unsubscribeFromEvent(eventId: String) {
        listeners[eventId]?.remove()
        listeners.remove(eventId)
    }

    fun sendMessage(eventId: String, message: ChatMessage) {
        viewModelScope.launch {
            db.collection("chats")
                .document(eventId)
                .collection("messages")
                .add(message)
        }
    }
}
