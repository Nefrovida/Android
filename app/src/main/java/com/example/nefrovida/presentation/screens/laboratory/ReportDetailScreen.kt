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
import androidx.compose.material3.SnackbarHost
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
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.di.NetworkModule
import com.example.nefrovida.ui.molecules.AnalysisNotFoundMessage
import java.io.File

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

    LaunchedEffect(patientAnalysisId) {
        viewModel.loadReport(patientAnalysisId)
    }

    fun openPdf(file: File) {
        try {
            android.widget.Toast.makeText(context, "Abriendo PDF...", android.widget.Toast.LENGTH_SHORT).show()

            // Verify file is actually a PDF by checking magic bytes
            val header = file.inputStream().use { it.readNBytes(5) }
            val isPdf = header.size >= 5 &&
                        header[0].toInt() == 0x25 && // %
                        header[1].toInt() == 0x50 && // P
                        header[2].toInt() == 0x44 && // D
                        header[3].toInt() == 0x46    // F

            if (!isPdf) {
                android.util.Log.e("ReportDetailScreen", "File is not a PDF! First bytes: ${header.joinToString()}")
                val preview = file.readText().take(200)
                android.util.Log.e("ReportDetailScreen", "File content preview: $preview")
                throw Exception("El archivo descargado no es un PDF válido")
            }

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            android.util.Log.e("ReportDetailScreen", "Error opening PDF", e)
            android.widget.Toast.makeText(context, "Error al abrir PDF: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    fun downloadAndOpenPdf(urlPath: String, fileName: String) {
        android.util.Log.d("ReportDetailScreen", "========== DOWNLOAD BUTTON CLICKED ==========")
        android.util.Log.d("ReportDetailScreen", "URL path: $urlPath")
        android.widget.Toast.makeText(context, "Descargando PDF...", android.widget.Toast.LENGTH_LONG).show()

        // Debug: Print all cookies before download
        NetworkModule.debugPrintAllCookies()

        // The URL comes as: /uploads/orina_20251021.pdf
        val baseUrl = "http://10.25.102.123:3001"
        val fullUrl = if (urlPath.startsWith("http")) {
            urlPath
        } else {
            val path = if (urlPath.startsWith("/")) urlPath else "/$urlPath"
            "$baseUrl$path"
        }

        android.util.Log.d("ReportDetailScreen", "Full URL: $fullUrl")

        val downloadsDir = File(context.getExternalFilesDir(null), "downloads")
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }
        val pdfFile = File(downloadsDir, fileName)

        viewModel.downloadPdf(fullUrl, pdfFile) { file ->
            openPdf(file)
        }
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
                android.util.Log.d("ReportDetailScreen", "Download button clicked!")
                android.widget.Toast.makeText(context, "Iniciando descarga...", android.widget.Toast.LENGTH_SHORT).show()

                // Get the PDF path from the report data
                val pdfPath = report.path
                android.util.Log.d("ReportDetailScreen", "PDF path from report: $pdfPath")

                if (pdfPath != null) {
                    downloadAndOpenPdf(pdfPath, "reporte_${patientAnalysisId}.pdf")
                } else {
                    android.widget.Toast.makeText(context, "No hay PDF disponible", android.widget.Toast.LENGTH_SHORT).show()
                }
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