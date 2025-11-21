package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String?,
    @SerializedName("role_id") val role: String?,
)
