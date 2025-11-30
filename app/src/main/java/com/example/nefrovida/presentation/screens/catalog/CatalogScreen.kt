package com.example.nefrovida.presentation.screens.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.domain.model.CatalogItem
import com.example.nefrovida.presentation.screens.catalog.comps.Catalog
import com.example.nefrovida.presentation.screens.catalog.comps.CatalogToggle

@Suppress("ktlint:standard:function-naming")
@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCatalog by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    val selectedList = uiState.selectedList
    val consultasList = uiState.consultaList
    val analysisList = uiState.analysisList

    if (showCatalog) {
        CatalogToggle(
            selectedList = uiState.selectedList ?: "Consultas",
            onTypeChange = { viewModel.updateSelectedList(it) },
            modifier = modifier,
        )

        Catalog(
            catalogList = if (selectedList == "Consultas") consultasList else analysisList,
            onCardClick = { catalogItem ->
                showDialog = true
                showCatalog = false
                viewModel.updateSelectedCatalogItem(catalogItem)
            },
        )
    }

    if (showDialog) {
        // TODO: Implement dialog for catalog item details
    }
}
