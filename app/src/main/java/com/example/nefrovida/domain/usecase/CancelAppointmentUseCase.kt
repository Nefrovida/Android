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
    // --- CORRECTION 1 ---
    //Function now accepts appointment ID
    operator fun invoke(token: String, appointmentId: Int): Flow<Result<Unit>> = flow {
        try {
            emit(Result.Loading)
            // --- CORRECTION 2 ---
            // Pass the ID to the repository
            val response = repository.cancelAppointmentById(token, appointmentId)

            // --- CORRECTION 3 ---
            // Handle the API response
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
