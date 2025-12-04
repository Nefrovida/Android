package com.example.nefrovida.data.repository

import android.util.Log
import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.AuthApiService
import com.example.nefrovida.data.remote.dto.ForgotPasswordRequest
import com.example.nefrovida.data.remote.dto.LoginRequest
import com.example.nefrovida.domain.model.User
import com.example.nefrovida.domain.repository.AuthRepository
import com.example.nefrovida.domain.repository.Result
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<User> =
        try {
            val response = authApiService.login(LoginRequest(username, password))

            if (response.isSuccessful && response.body() != null) {
                val user = response.body()!!.user.toDomain()
                Result.Success(user)
            } else {
                val errorMessage =
                    when (response.code()) {
                        401 -> "Credenciales inválidas"
                        404 -> "Usuario no encontrado"
                        500 -> "Error del servidor"
                        else -> "Error desconocido: ${response.code()}"
                    }
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "Error de conexión",
                exception = e,
            )
        }

    override suspend fun logout(): Result<Unit> =
        try {
            val response = authApiService.logout()

            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error("Error al cerrar sesión")
            }
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "Error de conexión",
                exception = e,
            )
        }

    override suspend fun register(request: com.example.nefrovida.data.remote.dto.RegisterRequest): Result<com.example.nefrovida.data.remote.dto.RegisterResponse> =
        try {
            val response = authApiService.register(request)

            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!)
            } else {
                val errorMessage =
                    when (response.code()) {
                        409 -> "El usuario ya existe o falta CURP"
                        400 -> "Datos inválidos"
                        500 -> "Error del servidor"
                        else -> "Error desconocido: ${response.code()}"
                    }
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "Error de conexión",
                exception = e,
            )
        }

    override suspend fun forgotPassword(username: String): Result<Unit> =
        try {
            val response = authApiService.forgotPassword(ForgotPasswordRequest(username))

            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                val errorMessage =
                    when (response.code()) {
                        404 -> "Usuario no encontrado"
                        500 -> "Error del servidor"
                        else -> "Error al solicitar recuperación de contraseña"
                    }
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Result.Error(
                message = e.message ?: "Error de conexión",
                exception = e,
            )
        }
}
