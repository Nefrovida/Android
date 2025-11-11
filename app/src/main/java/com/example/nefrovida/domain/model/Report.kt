package com.example.nefrovida.domain.model

data class Report (
    val resultId: Int,
    val patientAnalysisId: Int,
    val date: String,
    val path: String,
    val interpretation: String,
    val analysisName: String,


) {
    companion object {

        fun getMockData(): List<Report> =
            listOf(
                Report(
                    resultId = 1,
                    patientAnalysisId = 1,
                    date = "2025-11-07",
                    path = "/results/analysis_1.pdf",
                    interpretation = "Interpretation 1",
                    analysisName = "Analysis 1"
                ),
                Report(
                    resultId = 1,
                    patientAnalysisId = 1,
                    date = "2025-11-07",
                    path = "/results/analysis_1.pdf",
                    interpretation = "Interpretation 1",
                    analysisName = "Analysis 1"
                ),
                Report(
                    resultId = 1,
                    patientAnalysisId = 1,
                    date = "2025-11-07",
                    path = "/results/analysis_1.pdf",
                    interpretation = "Interpretation 1",
                    analysisName = "Analysis 1"
                ),
                Report(
                    resultId = 1,
                    patientAnalysisId = 1,
                    date = "2025-11-07",
                    path = "/results/analysis_1.pdf",
                    interpretation = "Interpretation 1",
                    analysisName = "Analysis 1"
                )

            )
    }
}