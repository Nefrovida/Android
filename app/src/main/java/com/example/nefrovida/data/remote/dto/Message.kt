package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("messageId")
    val messageId: Int,

    @SerializedName("content")
    val content: String,

    @SerializedName("likes")
    val likes: Int,

    @SerializedName("replies")
    val replies: Int,

    @SerializedName("forums") // Coincide con el JSON, pero lo mapearemos a 'forum'
    val forum: MessageForumInfo
)
