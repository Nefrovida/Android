package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.AnalysisHistory
import com.example.nefrovida.domain.repository.AnalysisHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnalysisHistoryUseCase
    @Inject
    constructor(
        private val repository: AnalysisHistoryRepository,
    ) {
        operator fun invoke(): Flow<Result<List<AnalysisHistory>>> =
            flow {
                try {
                    emit(Result.Loading)
                    val analysisHistory = repository.getAnalysisHistory()
                    emit(Result.Success(analysisHistory))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
