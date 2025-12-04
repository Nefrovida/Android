package com.example.nefrovida.presentation.screens.forum

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.usecase.PostLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumPostCardViewModel
    @Inject
    constructor(
        private val postLike: PostLikeUseCase,
    ) : ViewModel() {
        fun postLike(messageId: Int) {
            viewModelScope.launch {
                postLike.invoke(messageId = messageId).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                        }
                        is Result.Error -> {}
                    }
                }
            }
        }
    }
