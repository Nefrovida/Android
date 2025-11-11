package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Represents a structured error response from the backend.
 */
data class ErrorResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("error")
    val error: ErrorPayload
)

data class ErrorPayload(
    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String
)
