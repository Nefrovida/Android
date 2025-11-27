package com.example.nefrovida.presentation.screens.forum

import com.example.nefrovida.domain.model.MessageObj

data class MessageReplyUiState(
    val messageRepliesList: List<MessageObj> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
