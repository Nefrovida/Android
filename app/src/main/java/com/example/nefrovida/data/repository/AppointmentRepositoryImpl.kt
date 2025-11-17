package com.example.nefrovida.data.repository

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.repository.AppointmentRepository
import com.example.nefrovida.data.remote.api.AppointmentApi // Este import está bien
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val api: AppointmentApi
) : AppointmentRepository { // Ahora implementa la interfaz limpia

    override suspend fun getUserAppointments(token: String): List<AppointmentDto> {
        return api.getUserAppointments(token)
    }

    override suspend fun getAppointmentDetails(token: String, appointmentId: String): AppointmentDetailDto {
        return api.getAppointmentDetails(token, appointmentId)
    }
}