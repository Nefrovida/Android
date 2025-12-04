package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.ForumApiService
import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.MyForumItem
import com.example.nefrovida.data.remote.dto.Reply
import com.example.nefrovida.data.remote.dto.ReplyMessageRequest
import com.example.nefrovida.data.remote.dto.ReportUserRequest
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.MessageObj
import com.example.nefrovida.domain.repository.ForumRepository
import retrofit2.Response
import javax.inject.Inject

class ForumRepositoryImpl
    @Inject
    constructor(
        private val api: ForumApiService,
    ) : ForumRepository {
        override suspend fun getMyForums(): Response<List<MyForumItem>> = api.getMyForums()

        override suspend fun getForumFeed(
            page: Int,
            forumId: Int?,
        ): Response<List<Message>> = api.getForumFeed(page, forumId)

        override suspend fun getAllForums(
            page: Int,
            limit: Int,
            search: String?,
            isPublic: Boolean?,
        ): Response<List<ForumComplete>> = api.getAllForums(page, limit, search, isPublic)

        override suspend fun getMessage(messageId: Int): Message {
            val response = api.getMessage(messageId)
            return response
        }

        override suspend fun postMessage(
            forumId: Int,
            parentMessageId: Int,
            content: String,
        ): Boolean =
            api
                .postMessage(
                    forumId,
                    request = ReplyMessageRequest(parentMessageId, content),
                ).success

        override suspend fun getMessageReplies(
            forumId: Int,
            messageId: Int,
            page: Int,
            limit: Int,
        ): List<Message> {
            val response =
                api.getMessageReplies(
                    forumId,
                    messageId,
                    page,
                    limit,
                )
            return response.data.map { r ->
                r.toDomain()
            }
        }

        override suspend fun reportUser(
            userId: String,
            messageId: Int,
            cause: String,
        ): Result<Unit> =
            try {
                // Creamos el objeto request con los datos
                val request = ReportUserRequest(messageId = messageId, cause = cause)

                val response = api.reportUser(userId, request) // Pasamos el request a la API

                if (response.isSuccessful) {
                    Result.Success(Unit)
                } else {
                    Result.Error(Exception("Error al reportar"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
    }
