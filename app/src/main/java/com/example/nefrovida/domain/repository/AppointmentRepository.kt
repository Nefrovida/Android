package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response

interface AppointmentRepository {

    // --- Flujo Paciente (feature-13) ---
    suspend fun getUserAppointments(token: String): List<AppointmentDto>
    suspend fun getAppointmentDetails(token: String, appointmentId: String): AppointmentDetailDto

    // --- Flujo Secretaria (feature-16) ---
    // Acepta 'date'
    suspend fun getAppointmentListByDate(token: String, date: String): List<Appointment>
    // Acepta 'Int'
    suspend fun cancelAppointmentById(token: String, appointmentId: Int): Response<Unit>
    // Acepta 'Int'
    suspend fun getAppointmentById(token: String, appointmentId: Int): AppointmentDetailDto
}