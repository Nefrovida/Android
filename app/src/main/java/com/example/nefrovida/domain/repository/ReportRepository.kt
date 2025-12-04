package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.remote.dto.ApiResponse
import com.example.nefrovida.domain.model.Report

interface ReportRepository {
    suspend fun getReportByPatientAnalysisId(id: Int): ApiResponse<Report>
    fun getPdfUrl(patientAnalysisId: Int): String
}