package com.example.nefrovida.presentation.screens.agenda

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.usecase.CancelAppointmentUseCase
import com.example.nefrovida.domain.usecase.GetAppointmentFilteredListUseCase
import com.example.nefrovida.domain.usecase.GetAppointmentUseCase
import com.example.nefrovida.domain.usecase.GetDateAvailabilityUseCase
import com.example.nefrovida.domain.usecase.RescheduleAppointmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AgendaViewModel
    @Inject
    constructor(
        private val getAppointmentUseCase: GetAppointmentUseCase,
        private val cancelAppointmentUseCase: CancelAppointmentUseCase,
        private val getAppointmentFilteredListUseCase: GetAppointmentFilteredListUseCase,
        private val getDateAvailabilityUseCase: GetDateAvailabilityUseCase,
        private val rescheduleAppointmentUseCase: RescheduleAppointmentUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AgendaUiState())
        val uiState: StateFlow<AgendaUiState> = _uiState.asStateFlow()

        init {
            val today =
                java.text
                    .SimpleDateFormat("yyyy-MM-dd")
                    .format(java.util.Date())

            loadAgendaList(today)
        }

        fun loadAgendaList(date: String) {
            viewModelScope.launch {
                getAppointmentFilteredListUseCase(date).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading -> {
                                Log.d("AgendaVM", "Agenda List is loading")
                                state.copy(
                                    isLoading = true,
                                    selectedDate = date,
                                )
                            }

                            is Result.Success -> {
                                Log.d("AgendaVM", "Agenda List succeded")
                                state.copy(
                                    appointmentFilteredList = result.data,
                                    isLoading = false,
                                    error = null,
                                    selectedDate = date,
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
                                loadAgendaList(selected)
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

        suspend fun getDateAvailability(
            appointmentName: String,
            date: String,
        ): List<String> {
            var result: List<String> = emptyList()
            
            getDateAvailabilityUseCase(appointmentName, date).collect { state ->
                when (state) {
                    is Result.Loading -> {
                        // Loading state
                    }
                    is Result.Success -> {
                        result = state.data
                    }
                    is Result.Error -> {
                        result = emptyList()
                    }
                }
            }
            
            return result
        }

        fun rescheduleAppointment(
            id: Int,
            reason: String,
            date: String,
            time: String,
        ) {
            viewModelScope.launch {
                rescheduleAppointmentUseCase(
                    id,
                    reason,
                    date,
                    time,
                ).collect { result ->
                    when (result) {
                        is Result.Loading -> {
                            _uiState.update { state ->
                                state.copy(isLoading = true)
                            }
                        }
                        is Result.Success -> {
                            _uiState.value.selectedDate?.let { selected ->
                                loadAgendaList(selected)
                            }
                            _uiState.update { state ->
                                state.copy(
                                    selectedAppointment =
                                        state.selectedAppointment?.copy(
                                            status = AppointmentStatus.PROGRAMMED,
                                        ),
                                    isLoading = false,
                                    error = null,
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
    }
