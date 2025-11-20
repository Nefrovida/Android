package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnalysisHistoryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String,
    @SerializedName("recommendations") val recommendations: String? = null,
    @SerializedName("download_url") val downloadUrl: String? = null,
)
