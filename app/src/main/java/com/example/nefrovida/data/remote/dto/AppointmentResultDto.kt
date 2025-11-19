package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AppointmentResultDto (
    @SerializedName("name") val name:String,
    @SerializedName("url") val url:String,
)