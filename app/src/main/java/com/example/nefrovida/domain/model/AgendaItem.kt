package com.example.nefrovida.domain.model

sealed class AgendaItem {
    data class AppointmentItem(
        val appointment: Appointment,
    ) : AgendaItem()

    data class AnalysisItem(
        val analysis: PatientAnalysis,
    ) : AgendaItem()
}
