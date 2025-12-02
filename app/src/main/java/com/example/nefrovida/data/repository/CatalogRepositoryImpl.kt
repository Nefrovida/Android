package com.example.nefrovida.data.repository

import android.util.Log
import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.CatalogApi
import com.example.nefrovida.data.remote.dto.CreateAnalysisAppointmentRequest
import com.example.nefrovida.data.remote.dto.CreateAppointmentRequest
import com.example.nefrovida.domain.model.CatalogList
import com.example.nefrovida.domain.repository.CatalogRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogRepositoryImpl
    @Inject
    constructor(
        private val api: CatalogApi,
    ) : CatalogRepository {
        override suspend fun getCatalog(): CatalogList {
            val response = api.getCatalog()
            return CatalogList(
                appointments = response[0],
                analysis = response[1],
            )
        }

        override suspend fun createAppointment(
            patientId: String,
            doctorName: String,
            dateHour: String,
            duration: Int,
            appointmentType: String,
            place: String,
        ): Boolean {
            val request =
                CreateAppointmentRequest(
                    patientId = patientId,
                    doctorName = doctorName,
                    dateHour = dateHour,
                    duration = duration,
                    appointmentType = appointmentType,
                    place = place,
                )
            val response = api.createAppointment(request)
            return response.success
        }

        override suspend fun createAnalysisAppointment(
            userId: String,
            analysisId: Int,
            analysisDate: String,
            place: String,
        ): Boolean {
            val request =
                CreateAnalysisAppointmentRequest(
                    userId = userId,
                    analysisId = analysisId,
                    analysisDate = analysisDate,
                    place = place,
                )
            val response = api.createAnalysisAppointment(request)
            return response.success
        }

        override suspend fun getDateAvailability(
            doctorName: String,
            date: String,
        ): List<String> {
            val response = api.getDateAvailability(doctorName, date)

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                Log.e("CatalogRepository", "Failed to get date availability: ${response.code()}")
                return emptyList()
            }
        }
    }
