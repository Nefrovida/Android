package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.LoginRequest
import com.example.nefrovida.data.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @POST("auth/refresh")
    suspend fun refresh(): Response<Unit>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>
}

