package com.example.nefrovida.presentation.screens.catalog.comps

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nefrovida.domain.model.CatalogItem

@Composable
fun Catalog(
    catalogList: List<CatalogItem>,
    onCardClick: (CatalogItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(catalogList) { catalogItem ->
            CatalogCard(
                catalogItem = catalogItem,
                onCardClick = onCardClick
            )
        }
    }
}
