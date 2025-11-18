package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.common.Result.Loading
import com.example.nefrovida.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAppointmentUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    // --- CORRECTION 1 ---
    // Accepts the appointment ID
    operator fun invoke(token: String, appointmentId: Int): Flow<Result<AppointmentDetailDto>> = flow {
        try {
            emit(Result.Loading)
            // --- CORRECTION 2 ---
            // Passes the ID to the repository
            val appointment = repository.getAppointmentById(token, appointmentId)
            emit(Result.Success(appointment))
        } catch (e: HttpException) {
            // --- CORRECTION 3 ---
            // Passes the 'e' exception, no the 'e.message'
            emit(Result.Error(e))
        } catch (e: IOException) {
            // --- CORRECTION 4 ---
            // Passes the 'e' exception, no the string
            emit(Result.Error(e))
        }
    }
}
