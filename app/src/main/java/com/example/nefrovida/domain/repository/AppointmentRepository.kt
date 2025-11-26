package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.model.AppointmentsResult
import retrofit2.Response

interface AppointmentRepository {
    suspend fun getAppointmentList(): List<Appointment>

    suspend fun getAppointmentListByDate(
        date: String,
        userId: String,
    ): AppointmentsResult

    suspend fun getAppointmentById(id: Int): Appointment

    suspend fun cancelAppointmentById(id: Int): Response<Unit>
}
