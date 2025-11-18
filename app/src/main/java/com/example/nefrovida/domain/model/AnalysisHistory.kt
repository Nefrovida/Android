package com.example.nefrovida.domain.model

data class AnalysisHistory(
    val id: Int,
    val name: String,
    val specialty: String,
    val doctorName: String,
    val date: String,
    val recommendations: String? = null,
    val treatment: String? = null,
    val downloadUrl: String? = null
)
