package com.example.nefrovida.presentation.screens.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Uses Domain objects, not DTOs
data class PatientAgendaListState(
    val appointments: List<Appointment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

data class PatientAgendaDetailState(
    val appointment: Appointment? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class PatientAgendaViewModel
    @Inject
    constructor(
        private val repository: AppointmentRepository,
    ) : ViewModel() {
        private val _listState = MutableStateFlow(PatientAgendaListState())
        val listState: StateFlow<PatientAgendaListState> = _listState.asStateFlow()

        private val _detailState = MutableStateFlow(PatientAgendaDetailState())
        val detailState: StateFlow<PatientAgendaDetailState> = _detailState.asStateFlow()

        // No FAKE_TOKEN here

        init {
            loadAppointments()
        }

        fun loadAppointments() {
            viewModelScope.launch {
                _listState.value = PatientAgendaListState(isLoading = true)
                try {
                    // No token passed
                    val result = repository.getUserAppointments()
                    _listState.value = PatientAgendaListState(appointments = result)
                } catch (e: Exception) {
                    _listState.value = PatientAgendaListState(error = e.message)
                }
            }
        }

        fun loadAppointmentDetails(id: String) {
            viewModelScope.launch {
                _detailState.value = PatientAgendaDetailState(isLoading = true)
                try {
                    // No token passed
                    val result = repository.getAppointmentDetails(id)
                    _detailState.value = PatientAgendaDetailState(appointment = result)
                } catch (e: Exception) {
                    _detailState.value = PatientAgendaDetailState(error = e.message)
                }
            }
        }
    }
