package com.example.nefrovida.domain.model

import com.example.nefrovida.data.remote.dto.ServiceItemDto

data class CatalogList(
    val appointments: List<ServiceItemDto>,
    val analysis: List<ServiceItemDto>,
)
