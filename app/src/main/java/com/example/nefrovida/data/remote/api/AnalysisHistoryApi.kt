package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AnalysisHistoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AnalysisHistoryApi {
    @GET("historial/analysis")
    suspend fun getAnalysisHistory(): List<AnalysisHistoryDto>

    @GET("historial/analysis/{id}")
    suspend fun getAnalysisById(@Path("id") id: Int): AnalysisHistoryDto
}
