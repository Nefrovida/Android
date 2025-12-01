package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.CatalogList

interface CatalogRepository {
    suspend fun getCatalog(): CatalogList
}
