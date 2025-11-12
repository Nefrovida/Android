package com.example.nefrovida.data.repository

import com.example.nefrovida.data.remote.api.ApiService
import com.example.nefrovida.data.remote.dto.AddAnalysisRequest
import com.example.nefrovida.data.remote.dto.ErrorResponse
import com.example.nefrovida.data.remote.dto.toDomain
import com.example.nefrovida.domain.model.Analysis
import com.example.nefrovida.domain.repository.AnalysisRepository
import com.google.gson.Gson
import java.io.IOException
import javax.inject.Inject

class AnalysisRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson
) : AnalysisRepository {

    override suspend fun addAnalysis(
        name: String,
        description: String,
        previousRequirements: String,
        generalCost: Double,
        communityCost: Double
    ): Result<Analysis> {
        return try {
            val request = AddAnalysisRequest(name, description, previousRequirements, generalCost, communityCost)
            val response = apiService.addAnalysis(request)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                if (responseBody.success && responseBody.data != null) {
                    Result.success(responseBody.data.toDomain())
                } else {
                    Result.failure(Exception(responseBody.message ?: "El servidor reportó un error sin mensaje."))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                if (errorBody != null) {
                    try {
                        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                        Result.failure(IOException(errorResponse.error.message))
                    } catch (e: Exception) {
                        Result.failure(IOException("Error al decodificar la respuesta del servidor: ${response.code()}"))
                    }
                } else {
                    Result.failure(IOException("Error de red o del servidor: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
