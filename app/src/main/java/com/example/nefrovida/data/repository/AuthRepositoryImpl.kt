package com.example.nefrovida.data.repository

import android.util.Log
import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.AuthApiService
import com.example.nefrovida.data.remote.dto.LoginRequest
import com.example.nefrovida.domain.model.User
import com.example.nefrovida.domain.repository.AuthRepository
import com.example.nefrovida.domain.repository.Result

class AuthRepositoryImpl(
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
}
