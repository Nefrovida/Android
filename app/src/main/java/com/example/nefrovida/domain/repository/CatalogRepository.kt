package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.CatalogList

interface CatalogRepository {
    suspend fun getCatalog(): CatalogList

    suspend fun createAppointment(
        patientId: String,
        appointmentId: Int,
        dateHour: String,
        appointmentType: String,
    ): Boolean

    suspend fun createAnalysisAppointment(
        userId: String,
        analysisId: Int,
        analysisDate: String,
    ): Boolean

    suspend fun getDateAvailability(
        date: String,
        appointmentId: Int,
    ): List<String>

    suspend fun getAnalysisDateAvailability(
        analysisName: String,
        date: String,
    ): List<String>
}
