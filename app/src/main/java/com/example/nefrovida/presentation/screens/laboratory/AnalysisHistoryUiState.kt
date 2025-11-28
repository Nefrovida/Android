package com.example.nefrovida.presentation.screens.laboratory

import com.example.nefrovida.domain.model.AnalysisHistory

data class AnalysisHistoryUiState(
    val analysisHistoryList: List<AnalysisHistory> = emptyList(),
    val selectedAnalysis: AnalysisHistory? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
