package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.model.User
import com.example.nefrovida.domain.repository.AuthRepository
import com.example.nefrovida.domain.repository.Result

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        return authRepository.login(username, password)
    }
}

