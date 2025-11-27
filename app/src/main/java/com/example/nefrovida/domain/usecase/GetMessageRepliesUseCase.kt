package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.MessageObj
import com.example.nefrovida.domain.repository.ForumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMessageRepliesUseCase
    @Inject
    constructor(
        private val forumRepository: ForumRepository,
    ) {
        operator fun invoke(
            forumId: Int,
            messageId: Int,
            page: Int = 0,
            limit: Int = 10,
        ): Flow<Result<List<MessageObj>>> =
            flow {
                try {
                    emit(Result.Loading)
                    val responseList =
                        forumRepository.getMessageReplies(
                            forumId,
                            messageId,
                            page,
                            limit,
                        )
                    emit(Result.Success(responseList))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }
