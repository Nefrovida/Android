package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.CatalogList

interface CatalogRepository {
    suspend fun getCatalog(): CatalogList

    suspend fun createAppointment(
        patientId: String,
        appointmentId: Int,
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
        appointmentName: String,
        date: String,
    ): List<String>

    suspend fun getAnalysisDateAvailability(
        analysisName: String,
        date: String,
    ): List<String>
}
