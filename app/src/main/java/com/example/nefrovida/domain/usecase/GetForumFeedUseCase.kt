package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.domain.repository.ForumRepository
import retrofit2.Response
import javax.inject.Inject

class GetForumFeedUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(page: Int = 0, forumId: Int? = null): Response<List<Message>> = forumRepository.getForumFeed(page, forumId)
}
