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
    operator fun invoke(appointmentId: String): Flow<Result<AppointmentDetailDto>> = flow {
    }
}