package com.example.meetingapp.data.repository

import com.example.meetingapp.model.UserEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object UserRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun createUserIfNotExists(
        userId: String,
        email: String
    ) {
        try {
            val doc = db.collection("users").document(userId).get().await()

            if (!doc.exists()) {
                val user = UserEntity(
                    id = userId,
                    name = email.substringBefore("@"),
                    age = 0,
                    bio = "",
                    interests = emptyList(),
                    photos = emptyList(),
                    city = "",
                    country = ""
                )
                db.collection("users").document(userId).set(user).await()
            }
        } catch (e: Exception) {
            // Si falla, logueamos el error pero no crashea la app
            println("Error creando usuario: ${e.message}")
        }
    }


    suspend fun getUserById(userId: String): UserEntity? {
        return try {
            db.collection("users")
                .document(userId)
                .get()
                .await()
                .toObject(UserEntity::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun setUser(user: UserEntity) {
        try {
            db.collection("users").document(user.id).set(user).await()
        } catch (e: Exception) {
            println("Error guardando usuario: ${e.message}")
        }
    }

}
