package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response

interface AppointmentRepository {
    // Patient methods
    // Returns Domain Object (Appointment), NOT DTO
    suspend fun getUserAppointments(): List<Appointment>

    suspend fun getAppointmentDetails(appointmentId: String): Appointment

    // Secretary methods
    suspend fun getAppointmentById(id: Int): Appointment

    suspend fun getAppointmentListByDate(date: String): List<Appointment>

    suspend fun cancelAppointmentById(appointmentId: Int): Response<Unit>
}
