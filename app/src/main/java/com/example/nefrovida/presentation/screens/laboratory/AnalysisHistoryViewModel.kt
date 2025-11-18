package com.example.nefrovida.presentation.screens.laboratory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.usecase.GetAnalysisDetailsUseCase
import com.example.nefrovida.domain.usecase.GetAnalysisHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisHistoryViewModel
    @Inject
    constructor(
        private val getAnalysisHistoryUseCase: GetAnalysisHistoryUseCase,
        private val getAnalysisDetailsUseCase: GetAnalysisDetailsUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AnalysisHistoryUiState())
        val uiState: StateFlow<AnalysisHistoryUiState> = _uiState.asStateFlow()

        init {
            loadAnalysisHistory()
        }

        fun loadAnalysisHistory() {
            viewModelScope.launch {
                getAnalysisHistoryUseCase().collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                )

                            is Result.Success ->
                                state.copy(
                                    analysisHistoryList = result.data,
                                    isLoading = false,
                                    error = null,
                                )

                            is Result.Error ->
                                state.copy(
                                    error = result.exception.message,
                                    isLoading = false,
                                )
                        }
                    }
                }
            }
        }

        fun loadAnalysisDetails(id: Int) {
            viewModelScope.launch {
                getAnalysisDetailsUseCase(id).collect { result ->
                    _uiState.update { state ->
                        when (result) {
                            is Result.Loading ->
                                state.copy(
                                    isLoading = true,
                                )

                            is Result.Success ->
                                state.copy(
                                    selectedAnalysis = result.data,
                                    isLoading = false,
                                    error = null,
                                )

                            is Result.Error ->
                                state.copy(
                                    error = result.exception.message,
                                    isLoading = false,
                                )
                        }
                    }
                }
            }
        }

        fun clearError() {
            _uiState.update { it.copy(error = null) }
        }
    }
