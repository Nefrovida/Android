package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.AppointmentsResponse
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.model.AppointmentsResult
import com.example.nefrovida.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAppointmentFilteredListUseCase
    @Inject
    constructor(
        private val repository: AppointmentRepository,
    ) {
        operator fun invoke(
            date: String,
            userId: String,
        ): Flow<Result<AppointmentsResult>> =
            flow {
                try {
                    emit(Result.Loading)
                    val response = repository.getAppointmentListByDate(date, userId)
                    emit(Result.Success(response))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
