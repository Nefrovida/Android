package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

// Solo definimos las DOS rutas que el PACIENTE necesita
interface AppointmentApi {

    @GET("appointments")
    suspend fun getUserAppointments(
        @Header("Authorization") token: String
    ): List<AppointmentDto>

    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentDetails(
        @Header("Authorization") token: String,
        @Path("id") appointmentId: String
    ): AppointmentDetailDto
}