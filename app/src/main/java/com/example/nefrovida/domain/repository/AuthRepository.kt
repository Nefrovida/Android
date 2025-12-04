package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.User

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String, val exception: Throwable? = null) : Result<Nothing>()
}

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun logout(): Result<Unit>
    suspend fun register(request: com.example.nefrovida.data.remote.dto.RegisterRequest): Result<com.example.nefrovida.data.remote.dto.RegisterResponse>
    suspend fun forgotPassword(username: String): Result<Unit>
}

