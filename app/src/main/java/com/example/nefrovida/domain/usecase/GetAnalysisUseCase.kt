package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.PatientAnalysis
import com.example.nefrovida.domain.repository.AppointmentRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAnalysisUseCase
    @Inject
    constructor(
        private val repository: AppointmentRepository,
    ) {
        operator fun invoke(id: Int): Flow<Result<PatientAnalysis>> =
            flow {
                try {
                    emit(Result.Loading)
                    val analysis = repository.getAnalysisById(id)
                    emit(Result.Success(analysis))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
