package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.AuthRepository
import com.example.nefrovida.domain.repository.Result

class ForgotPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String): Result<Unit> {
        return authRepository.forgotPassword(username)
    }
}