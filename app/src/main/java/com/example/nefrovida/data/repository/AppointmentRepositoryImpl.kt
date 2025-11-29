package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.data.remote.dto.AppointmentsResponse
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.model.AppointmentsResult
import com.example.nefrovida.domain.model.PatientAnalysis
import com.example.nefrovida.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
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

        override suspend fun getAppointmentListByDate(
            date: String,
            userId: String,
        ): AppointmentsResult {
            val response = api.getAppointmentListByDate(date, userId)
            return AppointmentsResult(
                appointments = response.appointments.map { it.toDomain() },
                analysis = response.analysis.map { it.toDomain() },
            )
        }

        override suspend fun getAppointmentById(id: Int): Appointment = api.getAppointmentById(id).toDomain()

        override suspend fun getAnalysisById(id: Int): PatientAnalysis = api.getAnalysisById(id).toDomain()

        override suspend fun cancelAppointmentById(id: Int): Response<Unit> = api.cancelAppointment(id)
        // override suspend fun cancelAnalysisById(id: Int): Response<Unit> = api.cancelAnalysis(id)
    }
