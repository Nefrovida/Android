package com.example.nefrovida.data.remote

import AppointmentDto
import com.example.nefrovida.data.remote.dto.AppointmentDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API Service para obtener citas médicas
 */
interface AppointmentApiService {
    /**
     * Obtiene las citas programadas para un doctor
     * @param userId ID del user doctor en la sesion
     * @return Lista de citas
     */
    @GET("get-appointments/")
    suspend fun getAppointments,
    ): List<AppointmentDto>
}
