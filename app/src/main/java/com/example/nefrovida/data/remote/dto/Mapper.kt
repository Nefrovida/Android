package com.example.nefrovida.data.remote.dto

import com.example.nefrovida.domain.model.Analysis

fun AnalysisDto.toDomain(): Analysis {
    return Analysis(
        name = this.name,
        description = this.description,
        previousRequirements = this.previousRequirements,
        generalCost = this.generalCost,
        communityCost = this.communityCost
    )
}
