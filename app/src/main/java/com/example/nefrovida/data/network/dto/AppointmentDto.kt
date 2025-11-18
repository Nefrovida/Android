package com.example.nefrovida.data.network.dto

data class AppointmentDto(
    val id: Int,
    val date: String,
    val doctor: DoctorDto
    // "appointment_type" and "status" can be added if needed
)
