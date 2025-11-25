package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.ForumRepository
import javax.inject.Inject

class GetForumFeedUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(page: Int?, forumId: Int?) = forumRepository.getForumFeed(page, forumId)
}
