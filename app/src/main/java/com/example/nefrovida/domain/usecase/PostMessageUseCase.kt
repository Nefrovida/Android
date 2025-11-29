package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.ForumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostMessageUseCase
    @Inject
    constructor(
        private val forumRepository: ForumRepository,
    ) {
        operator fun invoke(
            forumId: Int,
            parentMessageId: Int,
            content: String,
        ): Flow<Result<Boolean>> =
            flow {
                try {
                    val responseStatus =
                        forumRepository.postMessage(
                            forumId,
                            parentMessageId,
                            content,
                        )
                    emit(Result.Success(responseStatus))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
