package com.example.nefrovida.presentation.screens.laboratory

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.presentation.common.Constants
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

    // Función para abrir el navegador
    val openPdfBrowser = { relativePath: String ->
        try {
            // 1. Construimos la URL completa usando tu constante
            val fullUrl = "${Constants.BASE_URL}$relativePath"

            Log.d("PDF_DEBUG", "Abriendo en navegador: $fullUrl")


            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(fullUrl)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e("PDF_ERROR", "Error al abrir link: ${e.message}")
            Toast.makeText(context, "No se pudo abrir el reporte", Toast.LENGTH_SHORT).show()
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
            val report = state.data

            ReportDetailContent(
                title = report.patientAnalysis.analysis.name,
                date = report.date,
                interpretation = report.interpretation,

                onDownloadClick = { openPdfBrowser(report.path) },
                onBackClick = onBackClick
            )
        }
    }
}