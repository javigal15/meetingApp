package com.example.meetingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.meetingapp.navigation.NavGraph
import com.example.meetingapp.ui.theme.MeetingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeetingAppTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}