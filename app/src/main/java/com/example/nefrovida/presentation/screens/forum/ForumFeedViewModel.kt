package com.example.nefrovida.presentation.screens.forum

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.domain.usecase.GetForumFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumFeedViewModel @Inject constructor(
    private val getForumFeedUseCase: GetForumFeedUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _forumFeed = mutableStateOf<List<Message>>(emptyList())
    val forumFeed: State<List<Message>> = _forumFeed

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _page = mutableStateOf(0)
    private var _canPaginate = true
    private val forumId: Int? = savedStateHandle.get("forumId")

    init {
        loadForumFeed(reset = true)
    }

    fun loadForumFeed(reset: Boolean = false) {
        if (_isLoading.value || (!_canPaginate && !reset)) return

        if (reset) {
            _page.value = 0
            _forumFeed.value = emptyList()
            _canPaginate = true
        }

        viewModelScope.launch {
            _isLoading.value = true
            val response = getForumFeedUseCase(_page.value, forumId)
            if (response.isSuccessful) {
                val newMessages = response.body() ?: emptyList()
                if (newMessages.isNotEmpty()) {
                    _forumFeed.value = _forumFeed.value + newMessages
                    _page.value++
                } else {
                    _canPaginate = false
                }
            }
            _isLoading.value = false
        }
    }
}
