package com.example.nefrovida.presentation.screens.notes

import com.example.nefrovida.domain.model.AppointmentNotes

data class AppointmentNotesUiState(
    val isLoading: Boolean = false,
    val appointmentNotes: List<AppointmentNotes> = emptyList(),
    val error: String? = null,
)
