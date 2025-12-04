package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.AnalysisHistory
import java.io.File

interface AnalysisHistoryRepository {
    suspend fun getAnalysisHistory(): List<AnalysisHistory>
    suspend fun getAnalysisById(id: Int): AnalysisHistory
    suspend fun downloadPdf(url: String, destinationFile: File): Boolean
}
