package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response

interface AppointmentRepository {

    // Flujo Paciente (usa IDs de String)
    suspend fun getUserAppointments(token: String): List<AppointmentDto>
    suspend fun getAppointmentDetails(token: String, appointmentId: String): AppointmentDetailDto

    // Flujo Secretaria (usa IDs de Int)
    suspend fun getAppointmentListByDate(token: String, date: String): List<Appointment>
    suspend fun cancelAppointmentById(token: String, appointmentId: Int): Response<Unit>
    suspend fun getAppointmentById(token: String, appointmentId: Int): AppointmentDetailDto
}