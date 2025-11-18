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
    suspend fun getAppointmentList(): List<Appointment>
    suspend fun getAppointmentListByDate(date: String): List<Appointment>
    suspend fun getAppointmentById(id: Int): Appointment
    suspend fun cancelAppointmentById(id: Int): Response<Unit>
}
