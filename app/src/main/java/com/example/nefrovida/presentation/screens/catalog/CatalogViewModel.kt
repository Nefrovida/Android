package com.example.nefrovida.presentation.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.model.CatalogItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(CatalogUIState())
        val uiState: StateFlow<CatalogUIState> = _uiState.asStateFlow()

        init {
            loadCatalog()
        }

        fun loadCatalog() {
            viewModelScope.launch {
                _uiState.update { state ->
                    state.copy(
                        consultaList =
                            listOf(
                                CatalogItem(
                                    id = 1,
                                    name = "Consulta",
                                    description = "Consulta médica general",
                                    generalCost = 100,
                                    internalCost = 50,
                                ),
                                CatalogItem(
                                    id = 2,
                                    name = "Cirugía",
                                    description = "Cirugía general",
                                    generalCost = 200,
                                    internalCost = 100,
                                ),
                            ),
                        analysisList = emptyList(),
                    )
                }
            }
        }

        fun updateSelectedList(type: String) {
            _uiState.update { state ->
                state.copy(selectedList = type)
            }
        }

        fun updateSelectedCatalogItem(catalogItem: CatalogItem) {
            _uiState.update { state ->
                state.copy(selectedCatalogItem = catalogItem)
            }
        }
    }
