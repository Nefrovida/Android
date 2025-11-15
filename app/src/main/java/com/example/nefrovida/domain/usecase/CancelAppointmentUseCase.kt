package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class cancelAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    operator fun invoke(id: Int): Flow<Result<Unit>> = flow {
        try {
            emit(Result.Loading)
            repository.cancelAppointmentById(id)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
