package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class cancelAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    operator fun invoke(id: Int): Flow<Result<Unit>> = flow {
        emit(Result.Loading)

        try {
            val response = repository.cancelAppointmentById(id)

            if (response.isSuccessful) {
                emit(Result.Success(Unit))
            } else {
                emit(Result.Error(Exception("HTTP ${response.code()}: ${response.message()}")))
            }

        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}

