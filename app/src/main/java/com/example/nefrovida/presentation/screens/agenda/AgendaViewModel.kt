package com.example.nefrovida.presentation.screens.agenda

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.AgendaItem
import com.example.nefrovida.domain.repository.UserPreferencesRepository
import com.example.nefrovida.domain.usecase.CancelAppointmentUseCase
import com.example.nefrovida.domain.usecase.GetAnalysisUseCase
import com.example.nefrovida.domain.usecase.GetAppointmentFilteredListUseCase
import com.example.nefrovida.domain.usecase.GetAppointmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@HiltViewModel
class AgendaViewModel
    @Inject
    constructor(
        private val getAppointmentUseCase: GetAppointmentUseCase,
        private val cancelAppointmentUseCase: CancelAppointmentUseCase,
        private val getAppointmentFilteredListUseCase: GetAppointmentFilteredListUseCase,
        private val userPreferencesRepository: UserPreferencesRepository,
        private val getAnalysisUseCase: GetAnalysisUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AgendaUiState())
        val uiState: StateFlow<AgendaUiState> = _uiState.asStateFlow()
        private val _userId = MutableStateFlow("")

        // expose variable as readOnly, updates automatically every time _userId changes
        val userId = _userId.asStateFlow()

        init {
            // get userId value from dataStore as coroutine
            viewModelScope.launch {
                userPreferencesRepository.userIdFlow.collect { id ->
                    _userId.value = id ?: ""

                    if (id != null && id.isNotBlank()) {
                        val today = SimpleDateFormat("yyyy-MM-dd").format(Date())
                        loadAgendaList(today, id)
                        Log.d("AgendaVMPrim", "fecha y user id: $today, $id")
                    }
                }
            }
        }

        fun loadAgendaList(
            date: String,
            userId: String,
        ) {
            // another coroutine, takes the value of userId previously loaded
            viewModelScope.launch {
                val currentUserId = _userId.value
                getAppointmentFilteredListUseCase(date, userId).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading -> {
                                Log.d("AgendaVMLoad", "$date, $currentUserId")
                                state.copy(
                                    isLoading = true,
                                    selectedDate = date,
                                )
                            }

                            is Result.Success -> {
                                Log.d("AgendaVM", "${result.data}")
                                state.copy(
                                    appointmentFilteredList = result.data,
                                    isLoading = false,
                                    error = null,
                                    selectedDate = date,
                                )
                            }

                            is Result.Error -> {
                                Log.e("AgendaVM", "ERROR: ${result.exception}")
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

        fun getAppointment(id: Int) {
            viewModelScope.launch {
                getAppointmentUseCase(id).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading -> {
                                state.copy(isLoading = true)
                            }
                            is Result.Success -> {
                                state.copy(
                                    selectedAppointment = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            }

                            is Result.Error -> {
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

        fun getAnalysis(id: Int) {
            viewModelScope.launch {
                getAnalysisUseCase(id).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading -> {
                                state.copy(isLoading = true)
                            }
                            is Result.Success -> {
                                state.copy(
                                    selectedAnalysis = result.data,
                                    isLoading = false,
                                    error = null,
                                )
                            }

                            is Result.Error -> {
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

        fun cancelAppointment(id: Int) {
            viewModelScope.launch {
                cancelAppointmentUseCase(id).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.update { state ->
                                state.copy(isLoading = true)
                            }
                        }

                        is Result.Success -> {
                            _uiState.value.selectedDate?.let { selected ->
                                _userId.value?.let { userId ->
                                    loadAgendaList(selected, userId)
                                }
                            }

                            _uiState.update { state ->
                                state.copy(
                                    selectedAppointment =
                                        state.selectedAppointment?.copy(
                                            status = AppointmentStatus.CANCELED,
                                        ),
                                    isLoading = false,
                                    error = null,
                                    showCancelSuccess = true,
                                )
                            }
                        }

                        is Result.Error -> {
                            _uiState.update { state ->
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

        fun resetCancelSuccess() {
            _uiState.update { it.copy(showCancelSuccess = false) }
        }

        fun selectItem(item: AgendaItem) {
            _uiState.update { it.copy(selectedItem = item) }
        }

        fun clearSelectedItem() {
            _uiState.update {
                it.copy(
                    selectedItem = null,
                    selectedAppointment = null,
                    selectedAnalysis = null,
                )
            }
        }
    }
