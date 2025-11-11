package com.example.nefrovida.domain.model

data class Analysis(
    val name: String,
    val description: String,
    val previousRequirements: String,
    val generalCost: Double,
    val communityCost: Double
)
