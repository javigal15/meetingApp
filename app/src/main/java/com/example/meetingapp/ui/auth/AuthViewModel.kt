package com.example.meetingapp.ui.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                Log.d("LOGIN", "onSuccess ejecutado")
                   onSuccess()
                    }
            .addOnFailureListener {
                Log.e("LOGIN", "Firebase auth failed: ${it.message}")
                onError(it.message ?: "Error de autenticación")
            }
    }
}