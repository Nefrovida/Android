package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AnalysisDto
import com.example.nefrovida.data.remote.dto.ApiResponse
import com.example.nefrovida.data.remote.dto.CreateAnalysisRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("analysis")
    suspend fun createAnalysis(@Body request: CreateAnalysisRequest): Response<ApiResponse<AnalysisDto>>

}
