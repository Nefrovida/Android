package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ServiceItemDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("generalCost") val generalCost: Int,
    @SerializedName("communityCost") val communityCost: Int,
    @SerializedName("doctor") val doctor: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("previousRequirements") val previousRequirements: String? = null,
)
