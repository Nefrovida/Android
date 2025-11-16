package com.example.nefrovida.presentation.screens.agenda

import com.example.nefrovida.domain.model.Appointment

data class AgendaUiState (
    val appointmentList: List<Appointment> = emptyList(),
    val appointmentFilteredList: List<Appointment> = emptyList(),
    val selectedAppointment: Appointment? = null,
    val selectedDate: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)