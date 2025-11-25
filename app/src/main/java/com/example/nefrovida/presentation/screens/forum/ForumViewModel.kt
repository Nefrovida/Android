package com.example.nefrovida.presentation.screens.forum

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.MyForumDto
import com.example.nefrovida.domain.usecase.GetMyForumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(
    private val getMyForumsUseCase: GetMyForumsUseCase,
) : ViewModel() {

    private val _myForums = MutableStateFlow<List<MyForumDto>>(emptyList())

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredMyForums: StateFlow<List<MyForumDto>> = combine(_myForums, _searchQuery) { forums, query ->
        if (query.isBlank()) {
            forums
        } else {
            forums.filter { it.forum.name.contains(query, ignoreCase = true) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    init {
        loadMyForums()
    }

    fun loadMyForums() {
        viewModelScope.launch {
            _isLoading.value = true
            val response = getMyForumsUseCase()
            if (response.isSuccessful) {
                _myForums.value = response.body() ?: emptyList()
            }
            _isLoading.value = false
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onSearch() {
        // La lógica de filtrado ya está en el combine, solo es necesario si se necesita una acción explícita
    }
}
