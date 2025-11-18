package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnalysisHistoryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("specialty") val specialty: String,
    @SerializedName("doctor_name") val doctorName: String,
    @SerializedName("date") val date: String,
    @SerializedName("recommendations") val recommendations: String? = null,
    @SerializedName("treatment") val treatment: String? = null,
    @SerializedName("download_url") val downloadUrl: String? = null
)
