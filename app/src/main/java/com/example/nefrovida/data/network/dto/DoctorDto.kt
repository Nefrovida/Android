package com.example.nefrovida.data.network.dto

import com.google.gson.annotations.SerializedName

data class DoctorDto(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val specialty: String
)
