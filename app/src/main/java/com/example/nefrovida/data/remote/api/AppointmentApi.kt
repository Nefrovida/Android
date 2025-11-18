package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AppointmentApi {
    // --- Flujo Paciente ---
    @GET("appointments")
    suspend fun getUserAppointments(@Header("Authorization") token: String): List<AppointmentDto>

    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentDetails(@Header("Authorization") token: String, @Path("id") appointmentId: String): AppointmentDetailDto

    // --- Flujo Secretaria (Las que faltaban) ---
    @GET("agenda/appointments/{date}")
    suspend fun getAppointmentListByDate(@Header("Authorization") token: String, @Path("date") date: String): List<Appointment>

    @PATCH("agenda/cancel/{id}")
    suspend fun cancelAppointmentById(@Header("Authorization") token: String, @Path("id") appointmentId: String): Response<Unit>

    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentById(@Header("Authorization") token: String, @Path("id") appointmentId: String): AppointmentDetailDto
}