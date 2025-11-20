package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class RescheduleAppointmentUseCase
    @Inject
    constructor(
        private val repository: AppointmentRepository,
    ) {
        operator fun invoke(
            id: Int,
            reason: String,
            date: String,
            time: String,
        ): Flow<Result<Unit>> =
            flow {
                emit(Result.Loading)

                val dateHour = "$date $time"

                try {
                    val response = repository.rescheduleAppointment(id, reason, dateHour)

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
