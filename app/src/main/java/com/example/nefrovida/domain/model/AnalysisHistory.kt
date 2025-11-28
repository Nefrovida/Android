package com.example.nefrovida.domain.model

data class AnalysisHistory(
    val id: Int,
    val name: String,
    val date: String,
    val interpretations: String? = null,
    val recommendations: String? = null,
    val downloadUrl: String? = null,
)
