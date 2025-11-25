package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.ForumRepository
import javax.inject.Inject

class GetMyForumsUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke() = forumRepository.getMyForums()
}
