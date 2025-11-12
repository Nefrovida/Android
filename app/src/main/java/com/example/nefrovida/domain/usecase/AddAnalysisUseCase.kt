package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.AnalysisRepository
import javax.inject.Inject

class AddAnalysisUseCase @Inject constructor(
    private val repository: AnalysisRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        previousRequirements: String,
        generalCost: Double,
        communityCost: Double
    ) = repository.addAnalysis(name, description, previousRequirements, generalCost, communityCost)
}
