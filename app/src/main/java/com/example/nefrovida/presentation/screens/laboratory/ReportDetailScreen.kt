package com.example.nefrovida.presentation.screens.laboratory

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
){
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(patientAnalysisId){
        viewModel.loadReport(patientAnalysisId)
    }

    when (val state = uiState) {
        ReportDetailUiState.Loading -> {
            Text("Cargando...")
        }

        is ReportDetailUiState.Error -> {
            AnalysisNotFoundMessage(state.message)
        }

        is ReportDetailUiState.Success -> {
            val report = (uiState as ReportDetailUiState.Success).data

            ReportDetailContent(
                title = report.patientAnalysis.analysis.name,
                date = report.date,
                interpretation = report.interpretation
            )
        }
    }


}