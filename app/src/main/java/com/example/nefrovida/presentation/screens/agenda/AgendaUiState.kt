package com.example.nefrovida.presentation.screens.agenda

import com.example.nefrovida.domain.model.AgendaItem
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.model.AppointmentsResult
import com.example.nefrovida.domain.model.PatientAnalysis

data class AgendaUiState(
    val appointmentList: List<Appointment> = emptyList(),
    val appointmentFilteredList: AppointmentsResult? = null,
    val selectedAppointment: Appointment? = null,
    val selectedAnalysis: PatientAnalysis? = null,
    val selectedItem: AgendaItem? = null,
    val showCancelSuccess: Boolean = false,
    val showRescheduleSuccess: Boolean = false,
    val selectedDate: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
