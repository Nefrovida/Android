package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toAppointment
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import retrofit2.Response
import javax.inject.Inject

class AppointmentRepositoryImpl
    @Inject
    constructor(
        private val api: AppointmentApi,
    ) : AppointmentRepository {
        override suspend fun getUserAppointments(): List<Appointment> {
            val dtos = api.getUserAppointments()
            return dtos.map { it.toAppointment() }
        }

        override suspend fun getAppointmentDetails(appointmentId: String): Appointment {
            val dto = api.getAppointmentDetails(appointmentId)
            return dto.toAppointment()
        }

        override suspend fun getAppointmentById(id: Int): Appointment = api.getAppointmentById(id.toString()).toAppointment()

        override suspend fun getAppointmentListByDate(date: String): List<Appointment> = api.getAppointmentListByDate(date)

        override suspend fun cancelAppointmentById(appointmentId: Int): Response<Unit> = api.cancelAppointmentById(appointmentId.toString())
    }
