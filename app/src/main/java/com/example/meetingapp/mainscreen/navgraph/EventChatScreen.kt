package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.meetingapp.model.ChatMessage
import com.example.meetingapp.model.Event
import com.example.meetingapp.mainscreen.meetingviewmodel.ChatViewModel

@Composable
fun EventChatScreen(
    event: Event,
    currentUser: String,
    chatViewModel: ChatViewModel
) {
    val messages by chatViewModel.chats.collectAsState()
    val eventMessages = messages[event.id] ?: emptyList()
    var inputText by remember { mutableStateOf("") }

    LaunchedEffect(event.id) { chatViewModel.subscribeToEvent(event.id) }

    DisposableEffect(Unit) {
        onDispose { chatViewModel.unsubscribeFromEvent(event.id) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            if (eventMessages.isEmpty()) {
                item { Text("No hay mensajes aún") }
            } else {
                items(eventMessages) { msg ->
                    Row(
                        horizontalArrangement = if (msg.sender == currentUser) Arrangement.End else Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${msg.sender}: ${msg.content}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje...") }
            )
            Button(onClick = {
                if (inputText.isNotBlank()) {
                    chatViewModel.sendMessage(
                        event.id,
                        ChatMessage(sender = currentUser, content = inputText)
                    )
                    inputText = ""
                }
            }) {
                Text("Enviar")
            }
        }
    }
}
