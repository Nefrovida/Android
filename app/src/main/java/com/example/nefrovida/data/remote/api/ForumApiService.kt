package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.ForumMessageDto
import com.example.nefrovida.data.remote.dto.MyForumDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForumApiService {
    @GET("forums/myForums")
    suspend fun getMyForums(): Response<List<MyForumDto>>

    @GET("forums/feed")
    suspend fun getForumFeed(
        @Query("page") page: Int?,
        @Query("forumId") forumId: Int?
    ): Response<List<ForumMessageDto>>
}
