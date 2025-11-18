package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.data.remote.dto.AppointmentDto
import com.example.nefrovida.data.remote.dto.AppointmentListDto
import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppointmentApi {
    // --- Flujo Paciente ---
    @GET("appointments")
    suspend fun getUserAppointments(@Header("Authorization") token: String): List<AppointmentDto>

    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentDetails(@Header("Authorization") token: String, @Path("id") id: String): AppointmentDetailDto
    // --- Flujo Secretaria ---
    @GET("secretary-agenda")
    suspend fun getAppointmentList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : List<AppointmentDto>
    @GET("agenda/appointments-per-day")
    suspend fun getAppointmentListByDate(
        @Query("date") date:String
    ): List<AppointmentDto>

    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentById(@Path("id") id: Int): AppointmentDto
    @POST("agenda/appointments/{id}/cancel")
    suspend fun cancelAppointment(@Path("id") id:Int): Response<Unit>
}