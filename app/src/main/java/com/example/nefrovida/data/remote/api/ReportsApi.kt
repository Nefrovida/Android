package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.ApiResponse
import com.example.nefrovida.data.remote.dto.ResultResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ReportsApi {

    @GET("report/get-result-android/{patient_analysis_id}")
    suspend fun getReportResult(
        @Path("patient_analysis_id") analysisId: Int
    ): ApiResponse<ResultResponse>
}
