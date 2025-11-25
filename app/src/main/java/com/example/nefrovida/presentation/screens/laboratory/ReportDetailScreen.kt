package com.example.nefrovida.presentation.screens.laboratory

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.ui.molecules.AnalysisNotFoundMessage

@Suppress("ktlint:standard:function-naming")
@Composable
fun ReportDetailScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    patientAnalysisId: Int,
    viewModel: ReportDetailViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()


    var showPdfViewer by remember { mutableStateOf(false) }
    var currentPdfPath by remember { mutableStateOf("") }


    LaunchedEffect(patientAnalysisId) {
        viewModel.loadReport(patientAnalysisId)
    }


    if (showPdfViewer) {
        PdfViewerScreen(
            relativePath = currentPdfPath,
            onBackClick = { showPdfViewer = false }
        )
        return
    }


    when (val state = uiState) {
        ReportDetailUiState.Loading -> {
            Text("Cargando...")
        }

        is ReportDetailUiState.Error -> {
            AnalysisNotFoundMessage(state.message)
        }

        is ReportDetailUiState.Success -> {
            val report = state.data

            ReportDetailContent(
                title = report.patientAnalysis.analysis.name,
                date = report.date,
                interpretation = report.interpretation,
                onDownloadClick = {
                    currentPdfPath = report.path
                    showPdfViewer = true
                },
                onBackClick = onBackClick
            )
        }
    }
}