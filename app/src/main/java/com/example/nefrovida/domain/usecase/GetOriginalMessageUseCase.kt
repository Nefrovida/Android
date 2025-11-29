package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.ForumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMessageUseCase
    @Inject
    constructor(
        private val repository: ForumRepository,
    ) {
        suspend operator fun invoke(messageId: Int): Flow<Result<Message>> =
            flow {
                try {
                    emit(Result.Loading)
                    val parentMessage = repository.getMessage(messageId)
                    emit(Result.Success(parentMessage))
                } catch (e: Exception) {
                    emit(Result.Error(exception = e))
                }
            }
    }
