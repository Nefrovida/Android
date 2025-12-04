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
            appointmentId: Int,
            dateHour: String,
            appointmentType: String,
        ): Boolean {
            val request =
                CreateAppointmentRequest(
                    patientId = patientId,
                    appointmentId = appointmentId,
                    dateHour = dateHour,
                    appointmentType = appointmentType,
                )
            val response = api.createAppointment(request)
            return response.success
        }

        override suspend fun createAnalysisAppointment(
            userId: String,
            analysisId: Int,
            analysisDate: String,
        ): Boolean {
            val request =
                CreateAnalysisAppointmentRequest(
                    userId = userId,
                    analysisId = analysisId,
                    analysisDate = analysisDate,
                )
            val response = api.createAnalysisAppointment(request)
            return response.success
        }

        override suspend fun getDateAvailability(
            date: String,
            appointmentId: Int,
        ): List<String> {
            val response = api.getDateAvailability(date, appointmentId)

            if (response.isSuccessful) {
                val appointments = response.body() ?: emptyList()
                val occupiedSlots = mutableSetOf<String>()

                // For each appointment, calculate all occupied time slots based on duration
                appointments.forEach { appointment ->
                    // Extract the time portion (HH:mm) from the ISO datetime string
                    val dateTime = appointment.dateHour
                    val timePart = dateTime.substringAfter('T').substringBefore(':')
                    val minutePart = dateTime.substringAfter('T').substringAfter(':').take(2)
                    val startHour = timePart.toInt()
                    val startMinute = minutePart.toInt()
                    val duration = appointment.duration

                    // Calculate all 10-minute slots occupied by this appointment
                    var currentMinutes = startHour * 60 + startMinute
                    val endMinutes = currentMinutes + duration

                    while (currentMinutes < endMinutes) {
                        val hour = currentMinutes / 60
                        val minute = currentMinutes % 60
                        occupiedSlots.add(String.format("%02d:%02d", hour, minute))
                        currentMinutes += 10 // Next 10-minute slot
                    }
                }

                return occupiedSlots.toList()
            } else {
                Log.e("CatalogRepository", "Failed to get date availability: ${response.code()}")
                return emptyList()
            }
        }

        override suspend fun getAnalysisDateAvailability(
            analysisName: String,
            date: String,
        ): List<String> {
            val response = api.getAnalysisDateAvailability(analysisName, date)

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                Log.e("CatalogRepository", "Failed to get analysis date availability: ${response.code()}")
                return emptyList()
            }
        }
    }
