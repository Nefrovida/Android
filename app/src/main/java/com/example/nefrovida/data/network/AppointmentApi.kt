package com.example.nefrovida.data.network

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AppointmentApi {

    // Esta ruta es para el paciente
    //
    @GET("appointments")
    suspend fun getUserAppointments(
        @Header("Authorization") token: String // La ruta está protegida
    ): List<AppointmentDto>

    // Esta ruta es para los detalles
    //
    @GET("agenda/appointment/{id}")
    suspend fun getAppointmentDetails(
        @Header("Authorization") token: String, // La ruta está protegida
        @Path("id") appointmentId: String
    ): AppointmentDetailDto
}
