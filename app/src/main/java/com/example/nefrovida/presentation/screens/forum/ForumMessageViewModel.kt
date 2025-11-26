package com.example.nefrovida.presentation.screens.forum

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.domain.usecase.GetMessageRepliesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForumMessageViewModel
    @Inject
    constructor(
        private val getReplies: GetMessageRepliesUseCase,
        stateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _messageReplies = MutableStateFlow<List<Message>>(emptyList())
        val messageReplies: StateFlow<List<Message>> = _messageReplies

        init {
            loadReplies()
        }

        private fun loadReplies() {
            viewModelScope.launch {
                getReplies().collect { result ->
                }
            }
        }
    }
