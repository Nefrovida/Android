package com.example.nefrovida.presentation.screens.forum

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.SimpleForumInfo
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.ForumRepository
import com.example.nefrovida.domain.usecase.GetAllForumsUseCase
import com.example.nefrovida.domain.usecase.GetForumFeedUseCase
import com.example.nefrovida.domain.usecase.GetMyForumsUseCase
import com.example.nefrovida.domain.usecase.PostLikeUseCase
import com.example.nefrovida.domain.usecase.PostNewMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.invoke

/**
 * ViewModel for the Forum screen.
 *
 * Manages the logic and state for the three tabs: "Discover", "My Forums", and "All Forums".
 * It handles data loading, pagination, search queries, and user interactions
 * such as joining a forum or posting a new message.
 *
 * @property getMyForumsUseCase Use case to get the user's forums.
 * @property getForumFeedUseCase Use case to get the message feed.
 * @property getAllForumsUseCase Use case to get all available forums.
 * @property postMessage Use case to post a new message.
 * @property forumRepository Repository for direct forum actions like joining a forum.
 */
@HiltViewModel
class ForumViewModel
    @Inject
    constructor(
        private val getMyForumsUseCase: GetMyForumsUseCase,
        private val getForumFeedUseCase: GetForumFeedUseCase,
        private val getAllForumsUseCase: GetAllForumsUseCase,
        private val postMessage: PostNewMessage,
        private val forumRepository: ForumRepository,
    ) : ViewModel() {
        /** The index of the currently selected tab in the UI. */
        private val _selectedTabIndex = MutableStateFlow(0)
        val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

        /** State indicating if a pull-to-refresh action is in progress. */
        private val _isRefreshing = MutableStateFlow(false)
        val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

        // --- State for the join forum dialog ---

        /** Controls the visibility of the confirmation dialog to join a forum. */
        private val _showJoinForumDialog = MutableStateFlow(false)
        val showJoinForumDialog: StateFlow<Boolean> = _showJoinForumDialog.asStateFlow()

        /** Stores the forum that the user has selected to join. */
        private val _forumToJoin = MutableStateFlow<ForumComplete?>(null)
        val forumToJoin: StateFlow<ForumComplete?> = _forumToJoin.asStateFlow()

        /** Represents the current state of the join forum operation (Loading, Success, Error). */
        private val _joinForumState = MutableStateFlow<Result<Unit>>(Result.Success(Unit))
        val joinForumState: StateFlow<Result<Unit>> = _joinForumState.asStateFlow()

        // --- State for the "Discover" tab ---

        /** The list of messages for the "Discover" feed. */
        private val _discoverFeed = MutableStateFlow<List<Message>>(emptyList())
        val discoverFeed: StateFlow<List<Message>> = _discoverFeed.asStateFlow()
        private val _discoverPage = mutableStateOf(0)
        private var _canDiscoverPaginate = true
        private val _isDiscoverLoading = mutableStateOf(false)
        val isDiscoverLoading: State<Boolean> = _isDiscoverLoading

        // --- State for the "My Forums" tab ---

        /** The list of forums the user belongs to. */
        private val _myForums = MutableStateFlow<List<SimpleForumInfo>>(emptyList())
        val myForums = _myForums.asStateFlow()

        /** The current search query for the "My Forums" tab. */
        private val _myForumsSearchQuery = MutableStateFlow("")
        val myForumsSearchQuery: StateFlow<String> = _myForumsSearchQuery.asStateFlow()
        private val _isMyForumsLoading = mutableStateOf(false)
        val isMyForumsLoading: State<Boolean> = _isMyForumsLoading

        /** The user's list of forums, filtered by [myForumsSearchQuery]. */
        val filteredMyForums: StateFlow<List<SimpleForumInfo>> =
            combine(_myForums, _myForumsSearchQuery) { forums, query ->
                if (query.isBlank()) {
                    forums
                } else {
                    forums.filter { it.name.contains(query, ignoreCase = true) }
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                emptyList(),
            )

        // --- State for the "All Forums" tab ---
        private val _allForums = MutableStateFlow<List<ForumComplete>>(emptyList())
        private val _allForumsPage = mutableIntStateOf(1) // Page is 1-indexed
        private var _canAllForumsPaginate = true

        /** The current search query for the "All Forums" tab. */
        private val _allForumsSearchQuery = MutableStateFlow("")
        val allForumsSearchQuery: StateFlow<String> = _allForumsSearchQuery.asStateFlow()
        private val _isAllForumsLoading = mutableStateOf(false)
        val isAllForumsLoading: State<Boolean> = _isAllForumsLoading

        /** The list of all forums, filtered by [allForumsSearchQuery]. */
        val filteredAllForums: StateFlow<List<ForumComplete>> =
            combine(_allForums, _allForumsSearchQuery) { forums, query ->
                if (query.isBlank()) {
                    forums
                } else {
                    forums.filter { it.name.contains(query, ignoreCase = true) }
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                emptyList(),
            )

        // --- State for posting a new message ---
        private val _postMessageError = MutableStateFlow("")
        val postMessageError = _postMessageError.asStateFlow()

        init {
            viewModelScope.launch {
                loadDiscoverFeed(reset = true)
                loadMyForums(reset = true)
                loadAllForums(reset = true)
            }
        }

        /**
         * Updates the index of the selected tab.
         * @param index The new tab index.
         */
        fun onTabSelected(index: Int) {
            _selectedTabIndex.value = index
        }

        /**
         * Refreshes the data for the currently visible tab.
         */
        fun refresh() {
            viewModelScope.launch {
                _isRefreshing.value = true
                when (_selectedTabIndex.value) {
                    0 -> loadDiscoverFeed(reset = true)
                    1 -> loadMyForums(reset = true)
                    2 -> loadAllForums(reset = true)
                }
                _isRefreshing.value = false
            }
        }

        /**
         * Loads the message feed for the "Discover" tab, handling pagination.
         * @param reset If true, resets pagination and clears the current list.
         */
        suspend fun loadDiscoverFeed(reset: Boolean = false) {
            if (_isDiscoverLoading.value || (!_canDiscoverPaginate && !reset)) return

            if (reset) {
                _discoverPage.value = 0
                _discoverFeed.value = emptyList()
                _canDiscoverPaginate = true
            }

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

        /**
         * Loads the list of forums the user belongs to.
         * @param reset If true, clears the current list before loading.
         */
        suspend fun loadMyForums(reset: Boolean = false) {
            if (_isMyForumsLoading.value && !reset) return

            if (reset) {
                _myForums.value = emptyList() // Clear for refresh
            }

            _isMyForumsLoading.value = true
            val response = getMyForumsUseCase()
            if (response.isSuccessful) {
                _myForums.value = response.body()?.mapNotNull { it.forum } ?: emptyList()
            }
            _isMyForumsLoading.value = false
        }

        /**
         * Updates the search query for "My Forums".
         * @param query The new search text.
         */
        fun onMyForumsSearchQueryChange(query: String) {
            _myForumsSearchQuery.value = query
        }

        /**
         * Loads the list of all available forums, handling pagination.
         * @param reset If true, resets pagination and clears the current list.
         */
        suspend fun loadAllForums(reset: Boolean = false) {
            if (_isAllForumsLoading.value || (!_canAllForumsPaginate && !reset)) return

            if (reset) {
                _allForumsPage.intValue = 1
                _allForums.value = emptyList()
                _canAllForumsPaginate = true
            }

            _isAllForumsLoading.value = true
            val response =
                getAllForumsUseCase(
                    page = _allForumsPage.intValue,
                    search = _allForumsSearchQuery.value.ifBlank { null },
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

        /**
         * Updates the search query for "All Forums" and reloads the list.
         * @param query The new search text.
         */
        fun onAllForumsSearchQueryChange(query: String) {
            _allForumsSearchQuery.value = query
            viewModelScope.launch {
                loadAllForums(reset = true)
            }
        }

        /**
         * Posts a new message to a forum.
         * @param forumId The ID of the forum where the message will be posted.
         * @param content The content of the message.
         */
        fun postNewMessage(
            forumId: Int,
            content: String,
        ) {
            viewModelScope.launch {
                postMessage.invoke(forumId, content).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            // TODO: Consider refreshing the feed or showing a confirmation.
                        }
                        is Result.Error -> {
                            _postMessageError.update { result.toString() }
                        }
                        is Result.Loading -> {
                            // A loading indicator could be shown if necessary.
                        }
                    }
                }
            }
        }

        /**
         * Handles the tap event on a forum from the "All Forums" list.
         * If the user is already a member, it allows navigation.
         * If not a member, it shows the confirmation dialog to join.
         *
         * @param forum The forum that was tapped.
         * @return `true` if navigation should proceed, `false` if the dialog should be shown.
         */
        fun onForumTapped(forum: ForumComplete): Boolean {
            val isMember = _myForums.value.any { it.forumId == forum.forumId }
            return if (isMember) {
                true // Navigate directly
            } else {
                _forumToJoin.value = forum
                _showJoinForumDialog.value = true
                false // Do not navigate
            }
        }

        /**
         * Confirms and executes the action to join the selected forum.
         * Makes the API call and updates the [joinForumState].
         * On success, it reloads the forum lists.
         */
        fun onJoinForumConfirm() {
            val forum = _forumToJoin.value ?: return
            viewModelScope.launch {
                _joinForumState.value = Result.Loading
                try {
                    val response = forumRepository.joinForum(forum.forumId)
                    if (response.isSuccessful && response.body()?.success == true) {
                        _joinForumState.value = Result.Success(Unit)
                        _showJoinForumDialog.value = false
                        // Update forum lists to reflect membership
                        loadAllForums(reset = true)
                        loadMyForums(reset = true)
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Failed to join forum"
                        _joinForumState.value = Result.Error(Exception(errorBody))
                    }
                } catch (e: Exception) {
                    _joinForumState.value = Result.Error(e)
                }
            }
        }

        /**
         * Closes and resets the state of the join forum dialog.
         */
        fun onJoinForumDismiss() {
            _showJoinForumDialog.value = false
            _forumToJoin.value = null
            _joinForumState.value = Result.Success(Unit) // Reset state
        }
    }