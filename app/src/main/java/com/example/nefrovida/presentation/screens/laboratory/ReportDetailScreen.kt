package com.example.nefrovida.presentation.screens.laboratory

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.ui.molecules.AnalysisNotFoundMessage

@Suppress("ktlint:standard:function-naming")
@Composable
fun ReportDetailScreen(
    patientAnalysisId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ReportDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val openPdfViewer = { pdfUrl: String ->
        val intent =
            Intent(Intent.ACTION_VIEW).apply {
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

    LaunchedEffect(patientAnalysisId) {
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

            Column {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar a Historial",
                        )
                    }
                    Text(
                        text = "Regresar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                ReportDetailContent(
                    title = report.patientAnalysis.analysis?.name ?: "Sin nombre",
                    date = report.date,
                    interpretation = report.interpretation,
                    recommendation = report.recommendation,
                    onDownloadClick = { openPdfViewer(pdfUrl) },
                    onBackClick = onBackClick,
                )
            }
        }
    }
}
