package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateAnalysisAppointmentRequest(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("analysis_id")
    val analysisId: Int,
    @SerializedName("analysis_date")
    val analysisDate: String,
)

data class CreateAnalysisAppointmentResponse(
    @SerializedName("success")
    val success: Boolean,
)
