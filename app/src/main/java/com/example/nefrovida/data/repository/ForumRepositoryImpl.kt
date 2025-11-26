package com.example.nefrovida.data.repository

import com.example.nefrovida.data.remote.api.ForumApiService
import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.MyForumItem
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

        override suspend fun getMessageReplies(
            forumId: Int,
            messageId: Int,
            page: Int,
            limit: Int,
        ): Response<List<Message>> =
            api.getMessageReplies(
                forumId = forumId,
                messageId = messageId,
                page = page,
                limit = limit,
            )
    }
