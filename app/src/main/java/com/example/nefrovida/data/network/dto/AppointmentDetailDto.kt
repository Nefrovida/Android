package com.example.nefrovida.data.network.dto

data class AppointmentDetailDto(
    val id: Int,
    val date: String,
    val doctor: DoctorDto,
    val requirements: String? // La API lo devuelve como un string
)
