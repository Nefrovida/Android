package com.example.nefrovida.domain.model

/**
 * Modelo de datos para representar una cita médica
 */
data class Appointment(
    val id: Int,
    val patientName: String,
    val hour: Int, // Hora en formato de 24 horas (ej: 8, 9, 10, etc.)
    val date: String, // Formato: "YYYY-MM-DD"
)
