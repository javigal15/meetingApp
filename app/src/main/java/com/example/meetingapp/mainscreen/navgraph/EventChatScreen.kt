package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.meetingapp.model.ChatMessage
import com.example.meetingapp.model.Event
import com.example.meetingapp.mainscreen.meetingviewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventChatScreen(
    event: Event,
    currentUser: String,
    chatViewModel: ChatViewModel
) {
    val messages by chatViewModel.chats.collectAsState()
    val eventMessages = messages[event.id] ?: emptyList()
    var inputText by remember { mutableStateOf("") }

    val listState = rememberLazyListState()

    LaunchedEffect(event.id) {
        chatViewModel.subscribeToEvent(event.id)
    }

    LaunchedEffect(eventMessages.size) {
        if (eventMessages.isNotEmpty()) {
            listState.animateScrollToItem(eventMessages.size - 1)
        }
    }

    DisposableEffect(Unit) {
        onDispose { chatViewModel.unsubscribeFromEvent(event.id) }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(event.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            "${event.participants.size} participantes",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Escribí un mensaje…") },
                    shape = RoundedCornerShape(24.dp),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            chatViewModel.sendMessage(
                                event.id,
                                ChatMessage(
                                    sender = currentUser,
                                    content = inputText
                                )
                            )
                            inputText = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar"
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            state = listState,
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            if (eventMessages.isEmpty()) {
                item {
                    Text(
                        text = "Todavía no hay mensajes 👋\n¡Rompé el hielo!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }

            items(eventMessages) { msg ->
                ChatBubble(
                    message = msg,
                    isMine = msg.sender == currentUser
                )
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage,
    isMine: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (isMine)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isMine) 16.dp else 0.dp,
                bottomEnd = if (isMine) 0.dp else 16.dp
            ),
            tonalElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                if (!isMine) {
                    Text(
                        text = message.sender,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isMine)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


