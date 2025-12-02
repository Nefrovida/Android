package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.CatalogList

interface CatalogRepository {
    suspend fun getCatalog(): CatalogList

    suspend fun createAppointment(
        patientId: String,
        doctorName: String,
        dateHour: String,
        duration: Int,
        appointmentType: String,
        place: String,
    ): Boolean

    suspend fun createAnalysisAppointment(
        userId: String,
        analysisId: Int,
        analysisDate: String,
        place: String,
    ): Boolean

    suspend fun getDateAvailability(
        doctorName: String,
        date: String,
    ): List<String>
}
