package com.example.nefrovida.presentation.screens.forum

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.usecase.PostLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumPostCardViewModel
    @Inject
    constructor(
        private val postLike: PostLikeUseCase,
    ) : ViewModel() {
        private val _messageReplies = MutableStateFlow(MessageReplyUiState())
        val messageReplies: StateFlow<MessageReplyUiState> = _messageReplies.asStateFlow()

        fun postLike(messageId: Int) {
            viewModelScope.launch {
                postLike.invoke(messageId = messageId).collect { result ->
                    when (result) {
                        is Result.Loading -> {}
                        is Result.Success -> {
                            Log.d("SUCCESS", "ALL GOOD")
                        }
                        is Result.Error -> {}
                    }
                }
            }
        }
    }
