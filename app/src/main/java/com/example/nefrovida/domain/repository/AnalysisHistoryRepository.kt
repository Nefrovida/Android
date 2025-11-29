package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.AnalysisHistory

interface AnalysisHistoryRepository {
    suspend fun getAnalysisHistory(): List<AnalysisHistory>
    suspend fun getAnalysisById(id: Int): AnalysisHistory
}
