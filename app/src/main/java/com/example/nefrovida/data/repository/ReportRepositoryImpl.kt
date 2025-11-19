package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.ReportsApi
import com.example.nefrovida.data.remote.dto.ApiResponse
import com.example.nefrovida.domain.model.Report
import com.example.nefrovida.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val api: ReportsApi,
) : ReportRepository {

    override suspend fun getReportByPatientAnalysisId(id: Int): ApiResponse<Report> {

        val response = api.getReportResult(id)

        if (!response.success) {
            throw Exception(response.error?.message ?: "Unknown error")
        }

        return ApiResponse(
            success = response.success,
            message = response.message,
            error = response.error,
            data = response.data?.toDomain()
        )
    }
}