package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.Appointment

class GetAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository,
){
    operator fun invoke(id: String): Flow<Result<Appointment>> =
        flow {
            try{
                emit(Result.Loading)
                val appointment = repository.getAppointmentById(id)
                emit(Result.Success(appointment))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
}