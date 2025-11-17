package com.example.nefrovida.data.repository

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.repository.AppointmentRepository
import com.example.nefrovida.data.network.AppointmentApi // <-- Usando tu nombre
import javax.inject.Inject

// Hilt sabe cómo crear 'api' gracias a tu AppModule
class AppointmentRepositoryImpl @Inject constructor(
    private val api: AppointmentApi 
) : AppointmentRepository {

    override suspend fun getUserAppointments(token: String): List<AppointmentDto> {
        return api.getUserAppointments(token)
    }

    override suspend fun getAppointmentDetails(token: String, appointmentId: String): AppointmentDetailDto {
        return api.getAppointmentDetails(token, appointmentId)
    }
}
