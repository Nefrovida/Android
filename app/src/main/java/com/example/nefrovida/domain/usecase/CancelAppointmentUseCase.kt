package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import retrofit2.Response

class CancelAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    // --- CORRECCIÓN 1 ---
    // La función ahora acepta el ID de la cita
    operator fun invoke(token: String, appointmentId: Int): Flow<Result<Unit>> = flow {
        try {
            emit(Result.Loading)
            // --- CORRECCIÓN 2 ---
            // Le pasamos el ID al repositorio
            val response = repository.cancelAppointmentById(token, appointmentId)

            // --- CORRECCIÓN 3 ---
            // Manejamos la respuesta de la API
            if (response.isSuccessful) {
                emit(Result.Success(Unit))
            } else {
                emit(Result.Error(HttpException(response)))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e))
        } catch (e: IOException) {
            emit(Result.Error(e))
        }
    }
}
