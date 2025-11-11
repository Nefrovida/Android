package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.AnalysisRepository
import javax.inject.Inject

class CreateAnalysisUseCase @Inject constructor(
    private val repository: AnalysisRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        previousRequirements: String,
        generalCost: Double,
        communityCost: Double
    ) = repository.createAnalysis(name, description, previousRequirements, generalCost, communityCost)
}
