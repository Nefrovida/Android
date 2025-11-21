package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response

interface AppointmentRepository {
    // Patient methods
    suspend fun getUserAppointments(): List<AppointmentDto>
    suspend fun getAppointmentDetails(appointmentId: String): AppointmentDetailDto

    // Secretary methods
    suspend fun getAppointmentById(id: Int): Appointment
    suspend fun getAppointmentListByDate(date: String): List<Appointment>
    suspend fun cancelAppointmentById(appointmentId: Int): Response<Unit>
}
