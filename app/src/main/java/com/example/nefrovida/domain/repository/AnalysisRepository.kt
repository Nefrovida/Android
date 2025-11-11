package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.Analysis

interface AnalysisRepository {
    suspend fun createAnalysis(
        name: String,
        description: String,
        previousRequirements: String,
        generalCost: Double,
        communityCost: Double
    ): Result<Analysis>
}
