package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.AnalysisHistory
import com.example.nefrovida.domain.repository.AnalysisHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnalysisDetailsUseCase
    @Inject
    constructor(
        private val repository: AnalysisHistoryRepository,
    ) {
        operator fun invoke(id: Int): Flow<Result<AnalysisHistory>> =
            flow {
                try {
                    emit(Result.Loading)
                    val analysisDetails = repository.getAnalysisById(id)
                    emit(Result.Success(analysisDetails))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
