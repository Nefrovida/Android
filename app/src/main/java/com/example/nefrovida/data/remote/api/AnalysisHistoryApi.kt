package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.AnalysisHistoryDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

interface AnalysisHistoryApi {
    @GET("historial/analysis")
    suspend fun getAnalysisHistory(): List<AnalysisHistoryDto>

    @GET("historial/analysis/{id}")
    suspend fun getAnalysisById(@Path("id") id: Int): AnalysisHistoryDto

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): ResponseBody
}
