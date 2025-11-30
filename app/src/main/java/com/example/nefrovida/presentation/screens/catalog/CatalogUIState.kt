package com.example.nefrovida.presentation.screens.catalog

import com.example.nefrovida.domain.model.CatalogItem

data class CatalogUIState(
    val consultaList: List<CatalogItem> = emptyList(),
    val analysisList: List<CatalogItem> = emptyList(),
    val selectedList: String? = "Consultas",
    val selectedCatalogItem: CatalogItem? = null,
)
