package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.Appointment

interface AppointmentRepository {
    suspend fun getAppointmentList(): List<Appointment>
    suspend fun getAppointmentListByDate(date: String): List<Appointment>
    suspend fun getAppointmentById(id: String): Appointment
    suspend fun cancelAppointmentById(id: String): Boolean

}
