package com.example.nefrovida.data.repository

import com.example.nefrovida.data.remote.api.ForumApiService
import com.example.nefrovida.data.remote.dto.ForumMessageDto
import com.example.nefrovida.data.remote.dto.MyForumDto
import com.example.nefrovida.domain.repository.ForumRepository
import retrofit2.Response
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val api: ForumApiService
) : ForumRepository {
    override suspend fun getMyForums(): Response<List<MyForumDto>> {
        return api.getMyForums()
    }

    override suspend fun getForumFeed(page: Int?, forumId: Int?): Response<List<ForumMessageDto>> {
        return api.getForumFeed(page, forumId)
    }
}
