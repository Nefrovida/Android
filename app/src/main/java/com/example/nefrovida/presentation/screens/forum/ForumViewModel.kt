package com.example.nefrovida.presentation.screens.forum

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.SimpleForumInfo
import com.example.nefrovida.domain.usecase.GetAllForumsUseCase
import com.example.nefrovida.domain.usecase.GetForumFeedUseCase
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
    private val getForumFeedUseCase: GetForumFeedUseCase,
    private val getAllForumsUseCase: GetAllForumsUseCase
) : ViewModel() {

    // --- State for Tab Selection ---
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
    }

    // --- State for "Descubrir" (General Feed) ---
    private val _discoverFeed = MutableStateFlow<List<Message>>(emptyList())
    val discoverFeed: StateFlow<List<Message>> = _discoverFeed.asStateFlow()
    private val _discoverPage = mutableStateOf(0)
    private var _canDiscoverPaginate = true
    private val _isDiscoverLoading = mutableStateOf(false)
    val isDiscoverLoading: State<Boolean> = _isDiscoverLoading

    // --- State for "Mis Foros" ---
    private val _myForums = MutableStateFlow<List<SimpleForumInfo>>(emptyList())
    private val _myForumsSearchQuery = MutableStateFlow("")
    val myForumsSearchQuery: StateFlow<String> = _myForumsSearchQuery.asStateFlow()
    private val _isMyForumsLoading = mutableStateOf(false)
    val isMyForumsLoading: State<Boolean> = _isMyForumsLoading

    val filteredMyForums: StateFlow<List<SimpleForumInfo>> = combine(_myForums, _myForumsSearchQuery) { forums, query ->
        if (query.isBlank()) {
            forums
        } else {
            forums.filter { it.name.contains(query, ignoreCase = true) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    // --- State for "Todos los Foros" ---
    private val _allForums = MutableStateFlow<List<ForumComplete>>(emptyList())
    private val _allForumsPage = mutableIntStateOf(1) // Page is 1-indexed
    private var _canAllForumsPaginate = true
    private val _allForumsSearchQuery = MutableStateFlow("")
    val allForumsSearchQuery: StateFlow<String> = _allForumsSearchQuery.asStateFlow()
    private val _isAllForumsLoading = mutableStateOf(false)
    val isAllForumsLoading: State<Boolean> = _isAllForumsLoading

    val filteredAllForums: StateFlow<List<ForumComplete>> = combine(_allForums, _allForumsSearchQuery) { forums, query ->
        if (query.isBlank()) {
            forums
        } else {
            forums.filter { it.name.contains(query, ignoreCase = true) }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    init {
        loadDiscoverFeed(reset = true)
        loadMyForums(reset = true)
        loadAllForums(reset = true)
    }

    // --- Functions for "Descubrir" ---
    fun loadDiscoverFeed(reset: Boolean = false) {
        if (_isDiscoverLoading.value || (!_canDiscoverPaginate && !reset)) return

        if (reset) {
            _discoverPage.value = 0
            _discoverFeed.value = emptyList()
            _canDiscoverPaginate = true
        }

        viewModelScope.launch {
            _isDiscoverLoading.value = true
            val response = getForumFeedUseCase(_discoverPage.value, null)
            if (response.isSuccessful) {
                val newMessages = response.body() ?: emptyList()
                if (newMessages.isNotEmpty()) {
                    _discoverFeed.value = _discoverFeed.value + newMessages
                    _discoverPage.value++
                } else {
                    _canDiscoverPaginate = false
                }
            }
            _isDiscoverLoading.value = false
        }
    }

    // --- Functions for "Mis Foros" ---
    fun loadMyForums(reset: Boolean = false) {
        if (_isMyForumsLoading.value && !reset) return

        if (reset) {
            _myForums.value = emptyList() // Clear for refresh
        }

        viewModelScope.launch {
            _isMyForumsLoading.value = true
            val response = getMyForumsUseCase()
            if (response.isSuccessful) {
                // Filtramos elementos donde 'forum' es nulo
                _myForums.value = response.body()?.mapNotNull { it.forum } ?: emptyList()
            }
            _isMyForumsLoading.value = false
        }
    }

    fun onMyForumsSearchQueryChange(query: String) {
        _myForumsSearchQuery.value = query
    }

    // --- Functions for "Todos los Foros" ---
    fun loadAllForums(reset: Boolean = false) {
        if (_isAllForumsLoading.value || (!_canAllForumsPaginate && !reset)) return

        if (reset) {
            _allForumsPage.intValue = 1 // 1-indexed for this endpoint
            _allForums.value = emptyList()
            _canAllForumsPaginate = true
        }

        viewModelScope.launch {
            _isAllForumsLoading.value = true
            val response = getAllForumsUseCase(
                page = _allForumsPage.intValue,
                search = _allForumsSearchQuery.value.ifBlank { null }
            )
            if (response.isSuccessful) {
                val newForums = response.body() ?: emptyList()
                if (newForums.isNotEmpty()) {
                    _allForums.value += newForums
                    _allForumsPage.intValue++
                } else {
                    _canAllForumsPaginate = false
                }
            }
            _isAllForumsLoading.value = false
        }
    }

    fun onAllForumsSearchQueryChange(query: String) {
        _allForumsSearchQuery.value = query
        loadAllForums(reset = true) // Reload all forums on search query change
    }



    fun onSearch() {
        // This function will be called from the SearchBar in ForumScreen
        // but the filtering logic is handled by combine for "Mis Foros" and "Todos los Foros"
        // and for "Descubrir" there is no search on content, only filtering by forumId which is not in this ViewModel.
        // It's effectively a no-op here for now, as search is handled by query change and combine.
    }
}
