package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.CreateAnalysisAppointmentRequest
import com.example.nefrovida.data.remote.dto.CreateAnalysisAppointmentResponse
import com.example.nefrovida.data.remote.dto.CreateAppointmentRequest
import com.example.nefrovida.data.remote.dto.CreateAppointmentResponse
import com.example.nefrovida.data.remote.dto.ServiceItemDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CatalogApi {
    @GET("patients/get-services")
    suspend fun getCatalog(): List<List<ServiceItemDto>>

    @POST("agenda/create-appointment")
    suspend fun createAppointment(
        @Body request: CreateAppointmentRequest,
    ): CreateAppointmentResponse

    @POST("analysis/analysis-appointment")
    suspend fun createAnalysisAppointment(
        @Body request: CreateAnalysisAppointmentRequest,
    ): CreateAnalysisAppointmentResponse
}
