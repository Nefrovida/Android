package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AppointmentApi {
    // --- Patient Methods ---
    @GET("appointments")
    suspend fun getUserAppointments(): List<AppointmentDto> // Token removed

    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentDetails(
        @Path("id") id: String,
    ): AppointmentDetailDto // Token removed

    // --- Secretary Methods
    @GET("agenda/appointments/{date}")
    suspend fun getAppointmentListByDate(
        @Path("date") date: String,
    ): List<Appointment>

    @PATCH("agenda/cancel/{id}")
    suspend fun cancelAppointmentById(
        @Path("id") appointmentId: String,
    ): Response<Unit>

    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentById(
        @Path("id") appointmentId: String,
    ): AppointmentDetailDto
}
