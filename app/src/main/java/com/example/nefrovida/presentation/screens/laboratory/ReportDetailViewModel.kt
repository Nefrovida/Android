package com.example.nefrovida.presentation.screens.laboratory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.model.Report
import com.example.nefrovida.domain.usecase.GetReportByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ReportDetailUiState {
    object Loading : ReportDetailUiState()
    data class Success(val data: Report) : ReportDetailUiState()
    data class Error(val message: String) : ReportDetailUiState()
}

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    private val getReportDetailUseCase: GetReportByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ReportDetailUiState>(ReportDetailUiState.Loading)
    val uiState: StateFlow<ReportDetailUiState> = _uiState

    fun loadReport(patientAnalysisId: Int) {
        viewModelScope.launch {
            _uiState.value = ReportDetailUiState.Loading

            try {
                val report = getReportDetailUseCase(patientAnalysisId)
                _uiState.value = ReportDetailUiState.Success(report.data!!)

            } catch (e: Exception) {
                _uiState.value = ReportDetailUiState.Error(
                    e.message ?: "Error desconocido"
                )
            }
        }
    }
}
