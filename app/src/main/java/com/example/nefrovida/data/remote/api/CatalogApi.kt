package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.ServiceItemDto
import retrofit2.http.GET

interface CatalogApi {
    @GET("patients/get-services")
    suspend fun getCatalog(): List<List<ServiceItemDto>>
}
