package com.example.nefrovida.ui.organisms

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.presentation.utils.formatDatePretty
import com.example.nefrovida.presentation.screens.forum.ForumPostCardViewModel

@Composable
fun ForumPostCard(
    post: Message,
    modifier: Modifier = Modifier,
    isOwnMessage: Boolean = false,
    onReportClick: (userId: String, messageId: Int) -> Unit = { _, _ -> },
    onClick: () -> Unit,
    viewModel: ForumPostCardViewModel = hiltViewModel(),
) {
    var showMenu by remember { mutableStateOf(false) }
    val liked = rememberSaveable(post.messageId) { mutableIntStateOf(post.liked) }

    LaunchedEffect(post) {
        liked.intValue = liked.intValue
    }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = post.senderName ?: "Usuario Anónimo",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = formatDatePretty(post.createdAt),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                Row(
                    modifier =
                        Modifier.clickable(
                            onClick = {
                                viewModel.postLike(post.messageId)
                                liked.intValue = if (liked.intValue == 0) 1 else 0
                            },
                        ),
                ) {
                    if (liked.intValue == 1) {
                        Icon(Icons.Default.Favorite, contentDescription = null)
                    } else {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null)
                    }
                    Text(text = "${post.likes + liked.intValue}", modifier = Modifier.padding(start = 4.dp))
                }

                if (!isOwnMessage) {
                    Box {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Opciones",
                            )
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                        ) {
                            DropdownMenuItem(
                                text = { Text("Reportar cuenta") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Report,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.error,
                                    )
                                },
                                onClick = {
                                    showMenu = false
                                    onReportClick(post.senderId, post.messageId)
                                },
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
