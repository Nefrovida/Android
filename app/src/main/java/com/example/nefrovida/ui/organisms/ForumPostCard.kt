package com.example.nefrovida.ui.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.remote.dto.Message


@Composable
fun ForumPostCard(
    modifier: Modifier = Modifier,
    post: Message,
    onClick: () -> Unit,
    isOwnMessage: Boolean = false,
    onReportClick: (String) -> Unit = {}
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White,
            ),
        onClick = onClick,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp),
        ) {
            post.forum?.name?.takeIf { it.isNotBlank() }?.let { name ->
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Likes")
                    Text(text = "${post.likes}", modifier = Modifier.padding(start = 4.dp))
                }
                Row {
                    Icon(imageVector = Icons.Default.Reply, contentDescription = "Replies")
                    Text(text = "${post.replies}", modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
    }
}

@Composable
fun MessageCard(
    message: Message,
    isOwnMessage: Boolean, // Para saber si ocultar el botón
    onReportClick: (String) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(...) {
        Row(...) {

        if (!isOwnMessage) {
            Box {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Reportar cuenta") },
                        onClick = {
                            showMenu = false
                            onReportClick(message.senderId) // Dispara la acción
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Report, contentDescription = null)
                        }
                    )
                }
            }
        }
    }
    }
}
