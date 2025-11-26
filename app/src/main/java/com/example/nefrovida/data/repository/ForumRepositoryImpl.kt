package com.example.nefrovida.data.repository

import com.example.nefrovida.data.remote.api.ForumApiService
import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.MyForumItem
import com.example.nefrovida.domain.repository.ForumRepository
import retrofit2.Response
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(
    private val api: ForumApiService
) : ForumRepository {
    override suspend fun getMyForums(): Response<List<MyForumItem>> {
        return api.getMyForums()
    }

    override suspend fun getForumFeed(page: Int, forumId: Int?): Response<List<Message>> {
        return api.getForumFeed(page, forumId)
    }

    override suspend fun getAllForums(
        page: Int,
        limit: Int,
        search: String?,
        isPublic: Boolean?
    ): Response<List<ForumComplete>> {
        return api.getAllForums(page, limit, search, isPublic)
    }
}
