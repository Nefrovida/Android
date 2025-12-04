package com.example.nefrovida.presentation.screens.forum

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.usecase.GetMessageRepliesUseCase
import com.example.nefrovida.domain.usecase.GetMessageUseCase
import com.example.nefrovida.domain.usecase.PostMessageUseCase
import com.example.nefrovida.domain.usecase.ReportUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumMessageViewModel
    @Inject
    constructor(
        private val getReplies: GetMessageRepliesUseCase,
        private val getMessage: GetMessageUseCase,
        private val postReply: PostMessageUseCase,
        private val reportUserUseCase: ReportUserUseCase,
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
                launch {
                    getMessage(messageId).collect { result ->
                        _messageReplies.update { state ->
                            when (result) {
                                is Result.Loading ->
                                    state.copy(
                                        isLoading = true,
                                    )
                                is Result.Success ->
                                    state.copy(
                                        parentMessage = result.data,
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
                launch {
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

        fun postReply(
            forumId: Int,
            parentMessageId: Int,
            content: String,
        ) {
            viewModelScope.launch {
                postReply.invoke(forumId, parentMessageId, content).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            loadReplies(forumId, parentMessageId)
                        }
                        is Result.Error -> {
                            _messageReplies.update {
                                it.copy(error = result.exception.message)
                            }
                        }
                        is Result.Loading -> {
                            // Nada
                        }
                    }
                }
            }
        }

        fun reportUser(userId: String) {
            viewModelScope.launch {
                // Opcional: Poner estado de 'loading'
                when (val result = reportUserUseCase(userId)) {
                    is Result.Success -> {
                        // Mostrar mensaje de éxito (usando un Channel o State para la UI)
                        _uiEvent.send(UiEvent.ShowSnackbar("Usuario reportado correctamente"))
                    }
                    is Result.Error -> {
                        _uiEvent.send(UiEvent.ShowSnackbar("Error al reportar usuario"))
                    }
                }
            }
        }
    }
