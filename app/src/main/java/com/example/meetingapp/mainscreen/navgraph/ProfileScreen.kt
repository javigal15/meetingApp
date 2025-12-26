package com.example.meetingapp.mainscreen.navgraph

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meetingapp.data.fake.FakeUser
import com.example.meetingapp.mainscreen.meetingviewmodel.MeetingsViewModel
import com.example.meetingapp.ui.profile.components.PhotoCarousel
import com.example.meetingapp.ui.profile.components.UserEventCard
import com.example.meetingapp.ui.profile.components.UserEventsSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: MeetingsViewModel,
    navController: NavController
) {
    val myEvents by viewModel.myEvents.collectAsState()
    val user = FakeUser.user

    // Estado para mostrar el BottomSheet de fotos
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showPhotos by remember { mutableStateOf(false) }

    if (showPhotos) {
        ModalBottomSheet(
            onDismissRequest = { showPhotos = false },
            sheetState = sheetState
        ) {
            PhotoCarousel(user.photos)
        }
    }

    // Obtener tamaño de pantalla
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val photoSize = screenHeight / 5

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            // HEADER
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                // FOTO PRINCIPAL REDONDA GRANDE
                if (user.photos.isNotEmpty()) {
                    Image(
                        painter = painterResource(user.photos.first()),
                        contentDescription = "Foto principal",
                        modifier = Modifier
                            .size(photoSize)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Edad: ${user.age}", style = MaterialTheme.typography.bodyMedium)

                    Button(onClick = { showPhotos = true }) {
                        Text("Fotos")
                    }
                }

                Text(
                    text = "${user.city}, ${user.country}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = user.bio,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(4.dp)
                )

                if (user.interests.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        user.interests.forEach { interest ->
                            AssistChip(onClick = {}, label = { Text(interest) })
                        }
                    }
                }
            }
        }
        // LISTA DE EVENTOS SIN REPETIR TÍTULO

        if (myEvents.isNotEmpty()) {
            item {
                Text(
                    text = "Mis eventos",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(myEvents) { event ->
                UserEventCard(
                    event = event,
                    onRemove = { viewModel.removeEvent(event) },
                    navController = navController
                )
            }
        }

    }
}
