package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAppointmentFilteredListUseCase
    @Inject
    constructor(
        private val repository: AppointmentRepository,
    ) {
        operator fun invoke(date: String): Flow<Result<List<Appointment>>> =
            flow {
                try {
                    emit(Result.Loading)
                    val appointmentList = repository.getAppointmentListByDate(date)
                    emit(Result.Success(appointmentList))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
