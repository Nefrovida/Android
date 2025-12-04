package com.example.nefrovida.presentation.screens.laboratory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.Report
import com.example.nefrovida.domain.repository.ReportRepository
import com.example.nefrovida.domain.usecase.DownloadAnalysisPdfUseCase
import com.example.nefrovida.domain.usecase.GetReportByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

sealed class ReportDetailUiState {
    object Loading : ReportDetailUiState()
    data class Success(val data: Report) : ReportDetailUiState()
    data class Error(val message: String) : ReportDetailUiState()
}

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    private val getReportDetailUseCase: GetReportByIdUseCase,
    private val reportRepository: ReportRepository,
    private val downloadAnalysisPdfUseCase: DownloadAnalysisPdfUseCase,
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

    fun getPdfUrl(patientAnalysisId: Int): String {
        return reportRepository.getPdfUrl(patientAnalysisId)
    }

    fun downloadPdf(url: String, destinationFile: File, onSuccess: (File) -> Unit) {
        android.util.Log.d("ReportDetailViewModel", "Starting PDF download from: $url")
        viewModelScope.launch {
            downloadAnalysisPdfUseCase(url, destinationFile).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        android.util.Log.d("ReportDetailViewModel", "Download in progress...")
                    }

                    is Result.Success -> {
                        android.util.Log.d("ReportDetailViewModel", "Download successful!")
                        onSuccess(result.data)
                    }

                    is Result.Error -> {
                        android.util.Log.e("ReportDetailViewModel", "Download failed: ${result.exception.message}")
                        // Could emit error state here if needed
                    }
                }
            }
        }
    }
}
