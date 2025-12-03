package com.example.nefrovida.presentation.screens.laboratory

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    LaunchedEffect(patientAnalysisId) {
        viewModel.loadReport(patientAnalysisId)
    }

    when (val state = uiState) {
        ReportDetailUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text("Cargando reporte...", modifier = Modifier.padding(top = 16.dp))
            }
        }

        is ReportDetailUiState.Error -> {
            AnalysisNotFoundMessage(state.message)
        }

        is ReportDetailUiState.Success -> {
            val report = (uiState as ReportDetailUiState.Success).data

            val onDownloadClick = {
                val pdfUrl = viewModel.getPdfUrl(patientAnalysisId)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfUrl))
                context.startActivity(intent)
            }

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
                    onDownloadClick = onDownloadClick,
                    onBackClick = onBackClick
                )
            }
        }
    }
}