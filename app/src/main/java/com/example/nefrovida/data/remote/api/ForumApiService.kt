package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.data.remote.dto.ForumMessageDto
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.MessageDto
import com.example.nefrovida.data.remote.dto.MyForumItem
import com.example.nefrovida.data.remote.dto.ReplyMessageRequest
import com.example.nefrovida.data.remote.dto.ReplyMessageStatus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ForumApiService {
    @GET("forums/myForums")
    suspend fun getMyForums(): Response<List<MyForumItem>>

    @GET("forums/feed")
    suspend fun getForumFeed(
        @Query("page") page: Int = 0,
        @Query("forumId") forumId: Int? = null,
    ): Response<List<Message>>

    @GET("forums")
    suspend fun getAllForums(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("search") search: String? = null,
        @Query("isPublic") isPublic: Boolean? = null,
    ): Response<List<ForumComplete>>

    @GET("forums/message/{messageId}")
    suspend fun getMessage(
        @Path("messageId") messageId: Int,
    ): Message

    @POST("forums/{forumId}/replies")
    suspend fun postMessage(
        @Path("forumId") forumId: Int,
        @Body request: ReplyMessageRequest,
    ): ReplyMessageStatus

    @GET("forums/{forumId}/messages/{messageId}/replies")
    suspend fun getMessageReplies(
        @Path("forumId") forumId: Int,
        @Path("messageId") messageId: Int,
        @Query("page") page: Int = 0,
        @Query("limit") limit: Int,
    ): MessageDto
}
