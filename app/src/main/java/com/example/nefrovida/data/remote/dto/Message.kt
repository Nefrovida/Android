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
    // Coincide con el JSON, pero lo mapearemos a 'forum'
    @SerializedName("forums")
    val forum: MessageForumInfo,
    @SerializedName("senderId")
    val senderId: String,
    @SerializedName("senderName")
    val senderName: String? = "Anónimo",
    @SerializedName("createdAt")
    val createdAt: String,
)

data class Reply(
    @SerializedName("id") val messageId: Int,
    @SerializedName("forumId") val forumId: Int,
    @SerializedName("content") val content: String,
    @SerializedName("stats") val stats: Stats,
)

data class Stats(
    @SerializedName("repliesCount") val replies: Int,
    @SerializedName("likesCount") val likes: Int,
)
