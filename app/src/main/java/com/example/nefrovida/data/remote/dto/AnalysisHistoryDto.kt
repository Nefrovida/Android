package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnalysisHistoryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String,
    @SerializedName("interpretations") val interpretations: String? = null,
    @SerializedName("recommendations") val recommendations: String? = null,
    @SerializedName("path") val downloadUrl: String? = null,  // Backend returns "path" not "download_url"
)
