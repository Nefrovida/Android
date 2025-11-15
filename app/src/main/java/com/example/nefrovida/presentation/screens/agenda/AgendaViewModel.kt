package com.example.nefrovida.presentation.screens.agenda

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.domain.usecase.GetAppointmentFilteredListUseCase
import com.example.nefrovida.domain.usecase.GetAppointmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.usecase.cancelAppointmentUseCase

@HiltViewModel
class AgendaViewModel @Inject constructor (
    private val getAppointmentUseCase : GetAppointmentUseCase,
    private val cancelAppointmentUseCase : cancelAppointmentUseCase,
    private val getAppointmentFilteredListUseCase : GetAppointmentFilteredListUseCase
    ) : ViewModel() {
    private val _uiState = MutableStateFlow(AgendaUiState())
    val uiState: StateFlow<AgendaUiState> = _uiState.asStateFlow()

    init {
        loadAgendaList("2025-11-24")
    }

    fun loadAgendaList(date: String) {
        viewModelScope.launch {
            getAppointmentFilteredListUseCase(date).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading -> {
                            Log.d("AgendaVM", "Loading appointments for date: $date")

                            state.copy(
                            isLoading = true

                        )}

                        is Result.Success -> {
                            Log.d("AgendaVM", "Appointments received: ${result.data}")
                            state.copy(
                            appointmentFilteredList = result.data,
                            isLoading = false,
                            error = null
                        )}

                        is Result.Error -> {
                            Log.e("AgendaVM", "Error fetching appointments", result.exception)

                            state.copy(
                            error = result.exception.message,
                            isLoading = false
                        )}
                    }
                }
            }
        }
    }

    fun getAppointment(id: String) {
        viewModelScope.launch {
            getAppointmentUseCase(id).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading -> state.copy(isLoading = true)
                        is Result.Success ->
                            state.copy(
                                selectedAppointment = result.data,
                                isLoading = false,
                                error = null
                            )

                        is Result.Error -> state.copy(
                            error = result.exception.message,
                            isLoading = false
                        )
                    }
                }
            }

        }
    }

    fun cancelAppointment(id: String) {
        viewModelScope.launch {
            cancelAppointmentUseCase(id).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading -> state.copy(isLoading = true)

                        is Result.Success -> state.copy(
                            selectedAppointment = state.selectedAppointment?.copy(
                                status = AppointmentStatus.CANCELED
                            ),
                            isLoading = false,
                            error = null
                        )

                        is Result.Error -> state.copy(
                            error = result.exception.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}