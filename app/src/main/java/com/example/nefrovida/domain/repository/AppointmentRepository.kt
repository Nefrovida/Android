package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response

interface AppointmentRepository {
    suspend fun getAppointmentList(): List<Appointment>
    suspend fun getAppointmentListByDate(date: String): List<Appointment>
    suspend fun getAppointmentById(id: Int): Appointment
    suspend fun cancelAppointmentById(id: Int): Response<Unit>

}
