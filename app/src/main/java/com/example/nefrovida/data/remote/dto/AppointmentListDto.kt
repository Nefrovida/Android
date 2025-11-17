package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AppointmentListDto (
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<AppointmentResultDto>
    )
