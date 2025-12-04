package com.example.nefrovida.presentation.screens.forum

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nefrovida.presentation.navigation.Screen
import com.example.nefrovida.ui.organisms.ForumPostCard
import com.example.nefrovida.ui.organisms.ParentMessage
import kotlin.collections.emptyList
import kotlin.collections.mutableListOf

data class ParentMessageInfo(
    val forumId: Int,
    val messageId: Int,
    val page: Int = 0,
    val limit: Int = 10,
)

@Composable
fun ForumMessageScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    pMI: ParentMessageInfo,
    viewModel: ForumMessageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.messageReplies.collectAsStateWithLifecycle()
    var replyText by remember { mutableStateOf("") }

    // Load replies when the parameters change
    LaunchedEffect(pMI) {
        viewModel.loadReplies(
            forumId = pMI.forumId,
            messageId = pMI.messageId,
            page = pMI.page,
            limit = pMI.limit,
        )
    }

    val listState = rememberLazyListState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Error: ${uiState.error}")
            }
        }

        else -> {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize(),
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                ) {
                    item {
                        uiState.parentMessage?.let { parent ->
                            ParentMessage(
                                modifier = Modifier.fillMaxWidth(),
                                post = parent,
                            )
                        }
                    }

                    items(uiState.messageRepliesList) { reply ->
                        ForumPostCard(
                            post = reply,
                            modifier = Modifier.padding(8.dp),
                            onClick = {
                                navController.navigate(
                                    Screen.Message.createRoute(
                                        forumId = pMI.forumId,
                                        messageId = reply.messageId,
                                    ),
                                )
                            },
                        )
                    }
                }

                // Reply input at the bottom
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = replyText,
                        onValueChange = {
                            if (it.length <= 5000) replyText = it
                        },
                        placeholder = { Text("Write a reply...") },
                        modifier = Modifier.weight(1f),
                        maxLines = 3,
                        isError = replyText.trim().isEmpty() && replyText.isNotEmpty(),
                        supportingText = {
                            Text("${replyText.length}/5000")
                        },
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    val isReplyValid = replyText.trim().isNotEmpty() && replyText.length <= 5000

                    IconButton(
                        onClick = {
                            if (isReplyValid) {
                                viewModel.postReply(pMI.forumId, pMI.messageId, replyText.trim())
                                replyText = ""
                            }
                        },
                        enabled = isReplyValid,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Responder",
                        )
                    }
                }
            }
        }
    }
}
