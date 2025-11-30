package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReplyMessageRequest(
    @SerializedName("parent_message_id")
    val parentMessageId: Int,
    @SerializedName("content")
    val content: String,
)

data class ReplyMessageStatus(
    val success: Boolean,
)
