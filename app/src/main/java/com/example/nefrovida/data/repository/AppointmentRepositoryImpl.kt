package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toAppointment
import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import retrofit2.Response
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val api: AppointmentApi
) : AppointmentRepository {

    override suspend fun getUserAppointments(): List<AppointmentDto> {
        return api.getUserAppointments()
    }

    override suspend fun getAppointmentDetails(appointmentId: String): AppointmentDetailDto {
        return api.getAppointmentDetails(appointmentId)
    }

    // Secretary implementations
    override suspend fun getAppointmentById(id: Int): Appointment {
        return api.getAppointmentDetails(id.toString()).toAppointment()
    }

    // ... implement the rest without token ...
}