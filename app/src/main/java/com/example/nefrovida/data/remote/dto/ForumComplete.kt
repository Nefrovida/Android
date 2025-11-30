package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForumComplete(
    @SerializedName("forum_id")
    val forumId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("public_status")
    val publicStatus: Boolean,

    @SerializedName("created_by")
    val createdBy: String,

    @SerializedName("active")
    val active: Boolean,

    @SerializedName("creation_date")
    val creationDate: String,

    @SerializedName("user")
    val creator: UserInfo
)
