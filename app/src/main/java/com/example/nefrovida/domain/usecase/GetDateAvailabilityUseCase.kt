package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDateAvailabilityUseCase
    @Inject
    constructor(
        private val repository: AppointmentRepository,
    ) {
        operator fun invoke(
            appointmentName: String,
            date: String,
        ): Flow<Result<List<String>>> =
            flow {
                try {
                    emit(Result.Loading)
                    val data = repository.getDateAvailability(appointmentName, date)
                    emit(Result.Success(data))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
