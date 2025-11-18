package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.common.Result.Loading
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {

    // =================================================================
    // VERSION 1: For Patient (feature-13)
    // =================================================================
    operator fun invoke(token: String, appointmentId: Int): Flow<Result<AppointmentDetailDto>> = flow {
        try {
            emit(Result.Loading)
            // Passes token and ID
            val appointment = repository.getAppointmentDetails(token, appointmentId.toString())
            emit(Result.Success(appointment))
        } catch (e: HttpException) {
            emit(Result.Error(e))
        } catch (e: IOException) {
            emit(Result.Error(e))
        }
    }

    // =================================================================
    // VERSIÓN 2: For Secretary (feature-16)
    // =================================================================
    operator fun invoke(id: Int): Flow<Result<Appointment>> = flow {
        try {
            emit(Result.Loading)
            val appointment = repository.getAppointmentByIdNoToken(id)
            emit(Result.Success(appointment))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
    }
}
