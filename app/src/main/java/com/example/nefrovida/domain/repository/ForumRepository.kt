package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.remote.dto.ForumMessageDto
import com.example.nefrovida.data.remote.dto.MyForumDto
import retrofit2.Response

interface ForumRepository {
    suspend fun getMyForums(): Response<List<MyForumDto>>

    suspend fun getForumFeed(page: Int?, forumId: Int?): Response<List<ForumMessageDto>>
}
