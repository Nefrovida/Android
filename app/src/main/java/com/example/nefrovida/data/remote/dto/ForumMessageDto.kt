package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForumMessageDto(
    @SerializedName("messageId")
    val messageId: Int,
    val content: String,
    val likes: Int,
    val replies: Int,
    val forums: ForumSummaryDto
)
