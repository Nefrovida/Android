package com.example.nefrovida.presentation.screens.forum

import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.Reply
import com.example.nefrovida.domain.model.MessageObj

data class MessageReplyUiState(
    val parentMessage: Message? = null,
    val messageRepliesList: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
