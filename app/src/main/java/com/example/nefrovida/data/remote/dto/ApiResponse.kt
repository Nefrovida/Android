package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * A generic wrapper for all API responses from the backend.
 * @param T The type of the data payload.
 */
data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: T?
)
