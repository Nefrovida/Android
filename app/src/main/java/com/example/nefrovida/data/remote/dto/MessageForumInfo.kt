package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MessageForumInfo(
    @SerializedName("forumId")
    val forumId: Int,

    @SerializedName("name")
    val name: String
)
