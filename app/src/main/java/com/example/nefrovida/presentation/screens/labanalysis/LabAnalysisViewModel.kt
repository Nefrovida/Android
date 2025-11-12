package com.example.nefrovida.presentation.screens.labanalysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.usecase.GetLabAnalysisListUseCase
import com.example.nefrovida.domain.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabAnalysisViewModel @Inject constructor(
    private val getLabAnalysisListUseCase: GetLabAnalysisListUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(LabAnalysisUIState())
    val uiState: StateFlow<LabAnalysisUIState> = _uiState.asStateFlow()

    init {
        loadLabAnalysisList()
    }

    fun loadLabAnalysisList(page: Int = 0) {
        viewModelScope.launch {
            getLabAnalysisListUseCase(page).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Loading -> state.copy(
                            isLoading = true
                        )
                        is Result.Success -> state.copy(
                            labAnalysisList = result.data,
                            isLoading = false,
                            error = null
                        )
                        is Result.Error -> state.copy(
                            error = result.exception.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}