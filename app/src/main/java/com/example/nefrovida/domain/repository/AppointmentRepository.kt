package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.remote.AppointmentApiService
import com.example.nefrovida.domain.model.Appointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repositorio para manejar operaciones relacionadas con citas médicas
 */
@Singleton
class AppointmentRepository
    @Inject
    constructor(
        private val apiService: AppointmentApiService,
    ) {
        /**
         * Obtiene las citas para un doctor en una fecha específica
         * @param doctorId ID del doctor
         * @param date Fecha en formato YYYY-MM-DD
         * @return Result con la lista de citas o error
         */
        suspend fun getAppointments(
            doctorId: Int,
            date: String,
        ): Result<List<Appointment>> =
            withContext(Dispatchers.IO) {
                try {
                    val response = apiService.getAppointments(doctorId)
                    val appointments = response.map { it.toDomain() }
                    Result.success(appointments)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            }
    }
