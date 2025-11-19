package com.example.nefrovida.presentation.screens.laboratory

import android.content.Intent
import android.net.Uri
import android.util.Log

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    val openPdfViewer = { pdfUrl: String ->
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(pdfUrl)
            type = "application/pdf"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            
            Log.e("PDF_VIEWER", "Fallo al abrir PDF: ${e.message}")
           
        }
    }

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
            val pdfUrl = report.path

            ReportDetailContent(
                title = report.patientAnalysis.analysis.name,
                date = report.date,
                interpretation = report.interpretation,
                onDownloadClick = { openPdfViewer(pdfUrl) },
                onBackClick = onBackClick
            )
        }
    }


}