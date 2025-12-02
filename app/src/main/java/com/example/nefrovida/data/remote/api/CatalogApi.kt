package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.CreateAnalysisAppointmentRequest
import com.example.nefrovida.data.remote.dto.CreateAnalysisAppointmentResponse
import com.example.nefrovida.data.remote.dto.CreateAppointmentRequest
import com.example.nefrovida.data.remote.dto.CreateAppointmentResponse
import com.example.nefrovida.data.remote.dto.ServiceItemDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CatalogApi {
    @GET("patients/get-services")
    suspend fun getCatalog(): List<List<ServiceItemDto>>

    @POST("appointments/schedule-appointment")
    suspend fun createAppointment(
        @Body request: CreateAppointmentRequest,
    ): CreateAppointmentResponse

    @POST("analysis/analysis-appointment")
    suspend fun createAnalysisAppointment(
        @Body request: CreateAnalysisAppointmentRequest,
    ): CreateAnalysisAppointmentResponse

    @GET("agenda/appointments/date-availability")
    suspend fun getDateAvailability(
        @Query("appointmentName") appointmentName: String,
        @Query("date") date: String,
    ): Response<List<String>>

    @GET("agenda/analyses/date-availability")
    suspend fun getAnalysisDateAvailability(
        @Query("analysisName") analysisName: String,
        @Query("date") date: String,
    ): Response<List<String>>
}
