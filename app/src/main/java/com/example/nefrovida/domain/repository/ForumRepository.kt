package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.MyForumItem
import com.example.nefrovida.domain.model.MessageObj
import retrofit2.Response

interface ForumRepository {
    suspend fun getMyForums(): Response<List<MyForumItem>>

    suspend fun getForumFeed(
        page: Int,
        forumId: Int?,
    ): Response<List<Message>>

    suspend fun getAllForums(
        page: Int,
        limit: Int,
        search: String?,
        isPublic: Boolean?,
    ): Response<List<ForumComplete>>

    suspend fun getMessage(messageId: Int): Message

    suspend fun postMessage(
        forumId: Int,
        parentMessageId: Int,
        content: String,
    ): Boolean

    suspend fun getMessageReplies(
        forumId: Int,
        messageId: Int,
        page: Int,
        limit: Int,
    ): List<Message>
}
