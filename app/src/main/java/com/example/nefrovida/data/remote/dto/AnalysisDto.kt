package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AnalysisDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("previousRequirements")
    val previousRequirements: String,
    @SerializedName("generalCost")
    val generalCost: Double,
    @SerializedName("communityCost")
    val communityCost: Double
)
