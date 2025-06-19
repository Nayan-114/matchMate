package com.example.metrimonialapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.metrimonialapp.data.UserProfileEntity
import com.example.metrimonialapp.viewmodel.UserViewModel

@Composable
fun ProfileMatchesScreen(viewModel: UserViewModel) {
    val list by viewModel.users.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list, key = { it.uuid }) { user ->
            MatchCard(
                user = user,
                onAccept  = { viewModel.setSelection(user.uuid, true) },
                onDecline = { viewModel.setSelection(user.uuid, false) }
            )
        }
    }
}

@Composable
private fun MatchCard(
    user: UserProfileEntity,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    val bgColor = when (user.selection) {
        true  -> Color(0xFFE8F5E9)
        false -> Color(0xFFFFEBEE)
        null  -> Color.White
    }

    Card(
        shape     = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors    = CardDefaults.cardColors(containerColor = bgColor),
        modifier  = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier           = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatarUrl)
                    .size(Size.ORIGINAL)
                    .crossfade(true)
                    .build(),
                contentDescription = "${user.fullName} avatar",
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )

            Spacer(Modifier.height(12.dp))

            // Name
            Text(
                text  = user.fullName,
                style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF5DADE2))
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text  = "${user.age}, ${user.city}, ${user.state}",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text  = "Match Score: ${user.score}%",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF43A047))
            )

            Spacer(Modifier.height(16.dp))

            if (user.selection == null) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDecline,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFECEFF1))
                    ) {
                        Icon(
                            imageVector    = Icons.Default.Close,
                            contentDescription = "Decline",
                            tint           = Color(0xFF78909C),
                            modifier       = Modifier.size(32.dp)
                        )
                    }

                    IconButton(
                        onClick = onAccept,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE8F5E9))
                    ) {
                        Icon(
                            imageVector    = Icons.Default.Check,
                            contentDescription = "Accept",
                            tint           = Color(0xFF43A047),
                            modifier       = Modifier.size(32.dp)
                        )
                    }
                }
            } else {
                val (icon, tint, label) = if (user.selection == true) {
                    Triple(Icons.Default.Check, Color(0xFF43A047), "Accepted")
                } else {
                    Triple(Icons.Default.Close, Color(0xFFB71C1C), "Declined")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector    = icon,
                        contentDescription = label,
                        tint           = tint,
                        modifier       = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text  = label,
                        color = tint,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
