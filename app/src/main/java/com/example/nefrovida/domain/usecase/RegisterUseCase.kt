package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.RegisterRequest
import com.example.nefrovida.data.remote.dto.RegisterResponse
import com.example.nefrovida.domain.repository.AuthRepository
import com.example.nefrovida.domain.repository.Result
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(request: RegisterRequest): Result<RegisterResponse> {
        return repository.register(request)
    }
}
