package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.AnalysisHistoryApi
import com.example.nefrovida.domain.model.AnalysisHistory
import com.example.nefrovida.domain.repository.AnalysisHistoryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalysisHistoryRepositoryImpl
    @Inject
    constructor(
        private val api: AnalysisHistoryApi,
    ) : AnalysisHistoryRepository {
        override suspend fun getAnalysisHistory(): List<AnalysisHistory> {
            val response = api.getAnalysisHistory()
            return response.map { it.toDomain() }
        }

        override suspend fun getAnalysisById(id: Int): AnalysisHistory {
            val response = api.getAnalysisById(id)
            return response.toDomain()
        }
    }
