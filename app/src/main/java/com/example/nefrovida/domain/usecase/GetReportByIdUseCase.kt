package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.ApiResponse
import com.example.nefrovida.data.remote.dto.ResultResponse
import com.example.nefrovida.domain.model.Report
import com.example.nefrovida.domain.repository.ReportRepository
import javax.inject.Inject

class GetReportByIdUseCase @Inject constructor(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(id: Int): ApiResponse<Report> {
        val response = repository.getReportByPatientAnalysisId(id)

        if (!response.success){
            throw Exception(response.error?.message ?: "Error desconocido")
        }

        return response
    }
}