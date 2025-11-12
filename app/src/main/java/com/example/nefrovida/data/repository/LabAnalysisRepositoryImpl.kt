package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.LaboratoryApi
import com.example.nefrovida.domain.model.LabAnalysis
import com.example.nefrovida.domain.repository.LabAnalysisRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LabAnalysisRepositoryImpl @Inject constructor(
    private val api: LaboratoryApi
) : LabAnalysisRepository {
    override suspend fun getLabAnalysisList(page: Int): List<LabAnalysis> {
        val response = api.getLabResults(page = page)
        return response.map { result ->
            result.toDomain()
        }
    }
}