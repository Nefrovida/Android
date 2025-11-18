package com.example.nefrovida.data.repository

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import com.example.nefrovida.data.remote.api.AppointmentApi
import retrofit2.Response
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val api: AppointmentApi
) : AppointmentRepository {

    // --- Flujo Paciente ---
    override suspend fun getUserAppointments(token: String): List<AppointmentDto> {
        return api.getUserAppointments(token)
    }

    override suspend fun getAppointmentDetails(token: String, appointmentId: String): AppointmentDetailDto {
        return api.getAppointmentDetails(token, appointmentId)
    }

    // --- Flujo Secretaria (Implementaciones nuevas) ---
    override suspend fun getAppointmentListByDate(token: String, date: String): List<Appointment> {
        return api.getAppointmentListByDate(token, date)
    }

    override suspend fun cancelAppointmentById(token: String, appointmentId: Int): Response<Unit> {
        // Convertimos Int a String para la API
        return api.cancelAppointmentById(token, appointmentId.toString())
    }

    override suspend fun getAppointmentById(token: String, appointmentId: Int): AppointmentDetailDto {
        // Convertimos Int a String para la API
        return api.getAppointmentById(token, appointmentId.toString())
    }
}