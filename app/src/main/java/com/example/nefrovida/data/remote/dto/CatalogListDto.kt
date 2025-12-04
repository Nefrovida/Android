package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CatalogListDto(
    @SerializedName("appointments") val appointments: List<ServiceItemDto>,
    @SerializedName("analysis") val analysis: List<ServiceItemDto>,
)
