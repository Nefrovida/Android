package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class JoinForumResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: JoinForumData?
)

data class JoinForumData(
    @SerializedName("userId") val userId: String,
    @SerializedName("forumId") val forumId: Int,
    @SerializedName("forumRole") val forumRole: String
)
