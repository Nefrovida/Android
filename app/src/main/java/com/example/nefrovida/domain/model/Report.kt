package com.example.nefrovida.domain.model

data class Report(
    val resultId: Int,
    val patientAnalysisId: Int,
    val date: String,
    val path: String,
    val interpretation: String,
    val recommendation: String,
    val patientAnalysis: PatientAnalysis,
)

data class PatientAnalysis(
    val patientAnalysisId: Int,
    val analysisDate: String,
    val resultsDate: String,
    val place: String,
    val duration: Int,
    val analysisStatus: AnalysisStatus,
    val analysis: Analysis,
)

data class Analysis(
    val analysisId: Int,
    val name: String,
    val description: String,
)

enum class AnalysisStatus {
    LAB,
    PENDING,
    SENT,
    REQUESTED,
}
