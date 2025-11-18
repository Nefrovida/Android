package com.example.nefrovida.data.repository

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.data.mapper.toDomain
import retrofit2.Response
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val api: AppointmentApi
) : AppointmentRepository {

    // --- Patient Section ---
    override suspend fun getUserAppointments(token: String): List<AppointmentDto> {
        return api.getUserAppointments(token)
    }

    override suspend fun getAppointmentDetails(token: String, appointmentId: String): AppointmentDetailDto {
        return api.getAppointmentDetails(token, appointmentId)
    }

    // --- Secretary Section (Corrected Implementations) ---
    override suspend fun getAppointmentList(): List<Appointment>{
        val response = api.getAppointmentList()
        return response.map { it.toDomain() }
    }
    override suspend fun getAppointmentListByDate(date: String): List<Appointment> {
        val response = api.getAppointmentListByDate(date)
        return response.map{it.toDomain()}
    }

    override suspend fun getAppointmentById(id: Int): Appointment{
        return api.getAppointmentById(id).toDomain()
    }

    override suspend fun cancelAppointmentById(id: Int) : Response<Unit> {
        return api.cancelAppointment(id)
    }
}
