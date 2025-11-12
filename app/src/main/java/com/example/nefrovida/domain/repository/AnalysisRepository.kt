package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.Analysis

interface AnalysisRepository {
    suspend fun addAnalysis(
        name: String,
        description: String,
        previousRequirements: String,
        generalCost: Double,
        communityCost: Double
    ): Result<Analysis>
}
