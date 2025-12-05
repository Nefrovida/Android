package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReportUserRequest(
    @SerializedName("messageId")
    val messageId: Int,
    @SerializedName("cause")
    val cause: String,
)
