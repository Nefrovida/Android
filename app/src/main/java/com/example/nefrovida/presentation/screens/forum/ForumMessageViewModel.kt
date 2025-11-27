package com.example.nefrovida.presentation.screens.forum

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.usecase.GetMessageRepliesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForumMessageViewModel
    @Inject
    constructor(
        private val getReplies: GetMessageRepliesUseCase,
    ) : ViewModel() {
        private val _messageReplies = MutableStateFlow(MessageReplyUiState())
        val messageReplies: StateFlow<MessageReplyUiState> = _messageReplies.asStateFlow()

        public fun loadReplies(
            forumId: Int,
            messageId: Int,
            page: Int = 0,
            limit: Int = 10,
        ) {
            viewModelScope.launch {
                getReplies(
                    forumId,
                    messageId,
                    page,
                    limit,
                ).collect { result ->
                    _messageReplies.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                )
                            is Result.Success ->
                                state.copy(
                                    messageRepliesList = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            is Result.Error ->
                                state.copy(
                                    error = result.exception.message,
                                    isLoading = false,
                                )
                        }
                    }
                }
            }
        }
    }
