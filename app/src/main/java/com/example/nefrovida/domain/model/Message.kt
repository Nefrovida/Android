package com.example.nefrovida.domain.model

data class MessageObj(
    val messageId: Int,
    val content: String,
    val likes: Int,
    val replies: Int,
    val forum: MessageForumInfoObj,
)

data class MessageForumInfoObj(
    val forumId: Int,
    val name: String,
)
