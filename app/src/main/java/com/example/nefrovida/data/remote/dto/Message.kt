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
    @SerializedName("liked")
    val liked: Int,
    @SerializedName("forums")
    val forum: MessageForumInfo,
    @SerializedName("senderId")
    val senderId: String? = null,
    @SerializedName("senderName")
    val senderName: String? = "Anónimo",
    @SerializedName("createdAt")
    val createdAt: String? = null,
)

data class Reply(
    @SerializedName("id") val messageId: Int,
    @SerializedName("liked") val liked: Int,
    @SerializedName("forumId") val forumId: Int,
    @SerializedName("content") val content: String,
    @SerializedName("stats") val stats: Stats,
    @SerializedName("senderId") val senderId: String? = null,
    @SerializedName("senderName") val senderName: String? = "Anónimo",
    @SerializedName("createdAt") val createdAt: String? = null,
)

data class Stats(
    @SerializedName("repliesCount") val replies: Int,
    @SerializedName("likesCount") val likes: Int,
)
