package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.common.Result.Loading
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.repository.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAppointmentFilteredListUseCase @Inject constructor(
    private val repository: AppointmentRepository
) {
    // --- CORRECCIÓN 1 ---
    // La función ahora acepta la fecha
    operator fun invoke(token: String, date: String): Flow<Result<List<Appointment>>> = flow {
        try {
            emit(Result.Loading)
            // --- CORRECCIÓN 2 ---
            // Le pasamos la fecha al repositorio
            val appointments = repository.getAppointmentListByDate(token, date)
            emit(Result.Success(appointments))
        } catch (e: HttpException) {
            emit(Result.Error(e))
        } catch (e: IOException) {
            emit(Result.Error(e))
        }
    }
}