package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.domain.repository.ForumRepository
import retrofit2.Response
import javax.inject.Inject

class GetAllForumsUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(
        page: Int = 1,
        limit: Int = 20,
        search: String? = null,
        isPublic: Boolean? = null
    ): Response<List<ForumComplete>> = forumRepository.getAllForums(page, limit, search, isPublic)
}
