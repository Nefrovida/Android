package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.domain.common.Result
// --- CORRECCIÓN 2 ---
// Importamos el 'Loading' que faltaba
import com.example.nefrovida.domain.common.Result.Loading
import com.example.nefrovida.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    // --- CORRECCIÓN 1 ---
    // Acepta el ID de la cita
    operator fun invoke(token: String, appointmentId: Int): Flow<Result<AppointmentDetailDto>> = flow {
        try {
            emit(Result.Loading)
            // --- CORRECCIÓN 3 ---
            // Pasa el ID al repositorio
            val appointment = repository.getAppointmentById(token, appointmentId)
            emit(Result.Success(appointment))
        } catch (e: HttpException) {
            // --- CORRECCIÓN 4 ---
            // Pasa la excepción 'e', no el 'e.message'
            emit(Result.Error(e))
        } catch (e: IOException) {
            // --- CORRECCIÓN 5 ---
            // Pasa la excepción 'e', no el string
            emit(Result.Error(e))
        }
    }
}