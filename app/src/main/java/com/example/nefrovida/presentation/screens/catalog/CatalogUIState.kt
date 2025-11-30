package com.example.nefrovida.presentation.screens.catalog

import com.example.nefrovida.data.remote.dto.ServiceItemDto

data class CatalogUIState(
    val isLoading: Boolean = false,
    val appointments: List<ServiceItemDto> = emptyList(),
    val analysis: List<ServiceItemDto> = emptyList(),
    val error: String? = null,
)
