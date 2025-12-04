package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.StatusMessage
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.ForumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostLikeUseCase
    @Inject
    constructor(
        private val forumRepository: ForumRepository,
    ) {
        operator fun invoke(messageId: Int): Flow<Result<StatusMessage>> =
            flow {
                try {
                    val response = forumRepository.postLike(messageId)
                    emit(Result.Success(response))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
