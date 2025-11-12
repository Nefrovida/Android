package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AddAnalysisRequest
import com.example.nefrovida.data.remote.dto.AnalysisDto
import com.example.nefrovida.data.remote.dto.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("analysis")
    suspend fun addAnalysis(@Body request: AddAnalysisRequest): Response<ApiResponse<AnalysisDto>>

}
