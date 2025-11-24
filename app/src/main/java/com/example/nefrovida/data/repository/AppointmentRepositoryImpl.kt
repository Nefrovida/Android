package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.data.remote.dto.RescheduleAppointmentDto
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Response

@Singleton
class AppointmentRepositoryImpl
    @Inject
    constructor(
        private val api: AppointmentApi,
    ) : AppointmentRepository {
        override suspend fun getAppointmentList(): List<Appointment> {
            val response = api.getAppointmentList()
            return response.map { it.toDomain() }
        }

        override suspend fun getAppointmentListByDate(date: String): List<Appointment> {
            val response = api.getAppointmentListByDate(date)
            return response.map { it.toDomain() }
        }

        override suspend fun getAppointmentById(id: Int): Appointment = api.getAppointmentById(id).toDomain()

        override suspend fun cancelAppointmentById(id: Int): Response<Unit> = api.cancelAppointment(id)

        override suspend fun getDateAvailability(
            appointmentName: String,
            date: String,
        ): List<String> {
            val response = api.getDateAvailability(appointmentName, date)

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                throw Exception("HTTP ${response.code()}: ${response.message()}")
            }
        }

        override suspend fun rescheduleAppointment(
            id: Int,
            reason: String,
            dateHour: String,
        ): Response<Unit> {
            val body = RescheduleAppointmentDto(reason, dateHour)
            return api.rescheduleAppointment(id, body)
        }
    }
