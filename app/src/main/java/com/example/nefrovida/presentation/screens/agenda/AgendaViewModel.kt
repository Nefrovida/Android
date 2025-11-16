package com.example.nefrovida.presentation.screens.agenda

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
        val today = java.text.SimpleDateFormat("yyyy-MM-dd")
            .format(java.util.Date())

        loadAgendaList(today)    }

    fun loadAgendaList(date: String) {
        viewModelScope.launch {
            getAppointmentFilteredListUseCase(date).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading -> {
                            state.copy(
                            isLoading = true,
                                selectedDate = date

                        )}

                        is Result.Success -> {
                            state.copy(
                            appointmentFilteredList = result.data,
                            isLoading = false,
                            error = null,
                                selectedDate = date
                        )}

                        is Result.Error -> {
                            state.copy(
                            error = result.exception.message,
                            isLoading = false
                        )}
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
                            state.copy(isLoading = true)}
                        is Result.Success -> {
                            state.copy(
                                selectedAppointment = result.data,
                                isLoading = false,
                                error = null
                            )}

                        is Result.Error -> {
                            state.copy(
                                error = result.exception.message,
                                isLoading = false
                            )}
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
                            _uiState.update {state ->
                            state.copy(isLoading = true)}}

                        is Result.Success -> {
                            _uiState.value.selectedDate?.let {
                                selected ->
                                loadAgendaList(selected)
                            }
                            _uiState.update {
                                state ->
                                state.copy(
                                    selectedAppointment = state.selectedAppointment?.copy(
                                        status = AppointmentStatus.CANCELED
                                    ),
                                    isLoading = false,
                                    error = null,
                                    showCancelSuccess = true,
                                )
                            }}

                        is Result.Error -> {
                            _uiState.update { state ->
                            state.copy(
                            error = result.exception.message,
                            isLoading = false
                        )}
                    }
                }
            }
        }
    }
    fun resetCancelSuccess() {
        _uiState.update { it.copy(showCancelSuccess = false) }
    }

}