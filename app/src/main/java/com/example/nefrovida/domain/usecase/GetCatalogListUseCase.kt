package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.model.CatalogList
import com.example.nefrovida.domain.repository.CatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCatalogListUseCase
    @Inject
    constructor(
        private val repository: CatalogRepository,
    ) {
        suspend operator fun invoke() = repository.getCatalog()
    }
