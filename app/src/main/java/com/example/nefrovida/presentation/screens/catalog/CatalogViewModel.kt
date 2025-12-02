package com.example.nefrovida.presentation.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.usecase.GetCatalogListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel
    @Inject
    constructor(
        private val getCatalogListUseCase: GetCatalogListUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CatalogUIState())
        val uiState = _uiState.asStateFlow()

        init {
            loadCatalog()
        }

        fun loadCatalog() {
            viewModelScope.launch {
                _uiState.value = uiState.value.copy(isLoading = true)
                try {
                    val result = getCatalogListUseCase()
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            analysis = result.analysis,
                            appointments = result.appointments,
                            error = null,
                        )
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = e.message,
                        )
                }
            }
        }
    }
