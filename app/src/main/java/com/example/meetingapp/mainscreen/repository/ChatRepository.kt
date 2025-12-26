package com.example.meetingapp.mainscreen.repository

import com.example.meetingapp.model.ChatMessage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ChatRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getChatCollection(eventId: String) =
        db.collection("events").document(eventId).collection("chats")

    suspend fun sendMessage(eventId: String, message: ChatMessage) {
        getChatCollection(eventId).add(message).await()
    }

    fun getMessages(eventId: String) =
        getChatCollection(eventId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
}
