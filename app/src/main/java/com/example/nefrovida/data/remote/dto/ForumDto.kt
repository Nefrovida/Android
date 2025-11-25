package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForumDto(
    @SerializedName("forum_id")
    val forumId: Int,
    val name: String
)
