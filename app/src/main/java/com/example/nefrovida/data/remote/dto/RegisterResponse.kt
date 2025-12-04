package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message") val message: String,
    @SerializedName("user") val user: UserSummaryDto,
    @SerializedName("pending") val pending: Boolean
)

data class UserSummaryDto(
    @SerializedName("user_id") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("role_id") val roleId: Int
)
