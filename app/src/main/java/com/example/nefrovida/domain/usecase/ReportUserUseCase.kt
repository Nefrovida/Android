package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.ForumRepository
import javax.inject.Inject

class ReportUserUseCase
    @Inject
    constructor(
        private val repository: ForumRepository,
    ) {
        suspend operator fun invoke(userId: String): Result<Unit> = repository.reportUser(userId)
    }
