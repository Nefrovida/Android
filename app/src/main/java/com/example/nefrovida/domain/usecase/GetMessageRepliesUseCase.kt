package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.domain.repository.ForumRepository
import retrofit2.Response
import javax.inject.Inject

class GetMessageRepliesUseCase
    @Inject
    constructor(
        private val forumRepository: ForumRepository,
    ) {
        suspend operator fun invoke(
            forumId: Int,
            messageId: Int,
            page: Int = 0,
            limit: Int = 10,
        ): Response<List<Message>> =
            forumRepository.getMessageReplies(
                forumId = TODO(),
                messageId = TODO(),
                page = TODO(),
                limit = TODO(),
            )
    }
