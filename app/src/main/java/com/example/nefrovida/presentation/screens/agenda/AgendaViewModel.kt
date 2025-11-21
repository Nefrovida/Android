package com.example.nefrovida.presentation.screens.agenda

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.AppointmentRepository
import com.example.nefrovida.domain.usecase.CancelAppointmentUseCase
import com.example.nefrovida.domain.usecase.GetAppointmentFilteredListUseCase
import com.example.nefrovida.domain.usecase.GetAppointmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AgendaListState(
    val appointments: List<AppointmentDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class AgendaDetailState(
    val appointment: AppointmentDetailDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class AgendaViewModel
    @Inject
    constructor(
        private val repository: AppointmentRepository,
    ) : ViewModel() {
        private val _listState = MutableStateFlow(AgendaListState())
        val listState: StateFlow<AgendaListState> = _listState.asStateFlow()

        private val _detailState = MutableStateFlow(AgendaDetailState())
        val detailState: StateFlow<AgendaDetailState> = _detailState.asStateFlow()

        private val FAKE_TOKEN = "Bearer TU_TOKEN_DE_PRUEBA_AQUI"

        init {
            loadAppointments()
        }

        fun loadAppointments() {
            viewModelScope.launch {
                _listState.value = AgendaListState(isLoading = true)
                try {
                    _listState.value =
                        AgendaListState(
                            appointments = repository.getUserAppointments(FAKE_TOKEN),
                        )
                } catch (e: Exception) {
                    _listState.value = AgendaListState(error = e.message)
                }
            }
        }

        fun loadAppointmentDetails(id: String) {
            viewModelScope.launch {
                _detailState.value = AgendaDetailState(isLoading = true)
                try {
                    _detailState.value =
                        AgendaDetailState(
                            appointment = repository.getAppointmentDetails(FAKE_TOKEN, id),
                        )
                } catch (e: Exception) {
                    _detailState.value = AgendaDetailState(error = e.message)
                }
            }
        }
    }

@HiltViewModel
class AgendaViewModel
    @Inject
    constructor(
        private val getAppointmentUseCase: GetAppointmentUseCase,
        private val cancelAppointmentUseCase: CancelAppointmentUseCase,
        private val getAppointmentFilteredListUseCase: GetAppointmentFilteredListUseCase,
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
    }
