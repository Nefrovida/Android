package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.StatusMessage
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.ForumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostNewMessage
    @Inject
    constructor(
        private val forumRepository: ForumRepository,
    ) {
        operator fun invoke(
            forumId: Int,
            content: String,
        ): Flow<com.example.nefrovida.domain.common.Result<StatusMessage>> =
            flow {
                try {
                    val responseStatus =
                        forumRepository.postMessage(
                            forumId,
                            content,
                        )
                    emit(Result.Success(responseStatus))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
