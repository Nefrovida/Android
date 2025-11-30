package com.example.nefrovida.data.remote.dto

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?,
    val error: ApiError?,
)

data class ApiError(
    val code: String,
    val message: String,
    val details: List<ValidationDetail>?,
)

data class ValidationDetail(
    val code: String,
    val message: String,
    val path: List<String>,
)

data class ResultResponse(
    val resultId: Int,
    val patientAnalysisId: Int,
    val date: String,
    val path: String,
    val interpretation: String,
    val patientAnalysis: PatientAnalysisDetail,
)

data class PatientAnalysisDetail(
    val type: String,
    val patientAnalysisId: Int,
    val analysisName: String,
    val analysisDate: String,
    val resultsDate: String?,
    val place: String,
    val duration: Int,
    val analysisStatus: AnalysisStatus,
    val analysis: AnalysisInfo?,
)

data class AnalysisInfo(
    val analysisId: Int,
    val name: String,
    val description: String,
)

enum class AnalysisStatus {
    LAB,
    PENDING,
    SENT,
    REQUESTED,
    CANCELED,
    PROGRAMMED,
}
