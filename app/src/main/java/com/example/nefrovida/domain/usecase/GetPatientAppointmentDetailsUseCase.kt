package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPatientAppointmentDetailsUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    operator fun invoke(token: String, appointmentId: Int): Flow<Result<AppointmentDetailDto>> = flow {
        try {
            emit(Result.Loading)
            val appointment = repository.getPatientAppointmentDetails(token, appointmentId.toString())
            emit(Result.Success(appointment))
        } catch (e: HttpException) {
            emit(Result.Error(e))
        } catch (e: IOException) {
            emit(Result.Error(e))
        }
    }
}