package com.example.nefrovida.domain.model

data class AppointmentsResult(
    val appointments: List<Appointment>,
    val analysis: List<PatientAnalysis>,
)
