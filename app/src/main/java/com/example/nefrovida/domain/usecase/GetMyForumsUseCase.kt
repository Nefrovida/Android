package com.example.nefrovida.domain.usecase

import com.example.nefrovida.data.remote.dto.MyForumItem
import com.example.nefrovida.domain.repository.ForumRepository
import retrofit2.Response
import javax.inject.Inject

class GetMyForumsUseCase @Inject constructor(
    private val forumRepository: ForumRepository
) {
    suspend operator fun invoke(): Response<List<MyForumItem>> = forumRepository.getMyForums()
}
