package com.example.nefrovida.data.network.dto

data class AppointmentDto(
    val id: Int,
    val date: String,
    val doctor: DoctorDto
    // puedes añadir "appointment_type" y "status" si los necesitas
)
