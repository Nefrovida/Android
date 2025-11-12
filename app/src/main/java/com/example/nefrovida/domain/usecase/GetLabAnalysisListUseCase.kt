package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.LabAnalysis
import com.example.nefrovida.domain.repository.LabAnalysisRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLabAnalysisListUseCase @Inject constructor(
    private val repository: LabAnalysisRepository
) {
    operator fun invoke(page: Int): Flow<Result<List<LabAnalysis>>> = flow {
        try {
            emit(Result.Loading)
            val labAnalysisList = repository.getLabAnalysisList(page)
            emit(Result.Success(labAnalysisList))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}