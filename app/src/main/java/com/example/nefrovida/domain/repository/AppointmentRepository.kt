package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto

interface AppointmentRepository {

    suspend fun getUserAppointments(token: String): List<AppointmentDto>

    suspend fun getAppointmentDetails(token: String, appointmentId: String): AppointmentDetailDto
}