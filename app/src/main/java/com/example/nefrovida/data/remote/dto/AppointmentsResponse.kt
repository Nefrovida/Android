package com.example.nefrovida.data.remote.dto

data class AppointmentsResponse(
    val appointments: List<AppointmentDto>,
    val analysis: List<PatientAnalysisDetail>,
)
