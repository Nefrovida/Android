package com.example.nefrovida.presentation.screens.laboratory

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.di.NetworkModule
import com.example.nefrovida.ui.organisms.NfBottomNavigationBar
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisDetailScreen(
    analysisId: Int,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AnalysisHistoryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Load analysis details when screen is first displayed
    LaunchedEffect(analysisId) {
        viewModel.loadAnalysisDetails(analysisId)
    }

    // Show error messages
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    fun openPdf(file: File) {
        try {
            // Verify file is actually a PDF by checking magic bytes
            val header = file.inputStream().use { it.readNBytes(5) }
            val isPdf = header.size >= 5 &&
                        header[0].toInt() == 0x25 && // %
                        header[1].toInt() == 0x50 && // P
                        header[2].toInt() == 0x44 && // D
                        header[3].toInt() == 0x46    // F

            if (!isPdf) {
                android.util.Log.e("AnalysisDetailScreen", "File is not a PDF! First bytes: ${header.joinToString()}")
                // Read a bit of the file to see what it actually contains
                val preview = file.readText().take(200)
                android.util.Log.e("AnalysisDetailScreen", "File content preview: $preview")
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
            android.util.Log.e("AnalysisDetailScreen", "Error opening PDF", e)
            e.printStackTrace()
        }
    }

    fun downloadAndOpenPdf(urlPath: String, fileName: String) {
        // Debug: Print all cookies before download
        android.util.Log.d("AnalysisDetailScreen", "========== BEFORE DOWNLOAD - DEBUG COOKIES ==========")
        NetworkModule.debugPrintAllCookies()

        // The URL comes as: /uploads/orina_20251021.pdf
        // We need to construct the full URL using the base URL
        val baseUrl = "http://10.25.102.123:3001"
        val fullUrl = if (urlPath.startsWith("http")) {
            urlPath
        } else {
            // Ensure path starts with /
            val path = if (urlPath.startsWith("/")) urlPath else "/$urlPath"
            "$baseUrl$path"
        }

        android.util.Log.d("AnalysisDetailScreen", "Original path from server: $urlPath")
        android.util.Log.d("AnalysisDetailScreen", "Full URL constructed: $fullUrl")

        val downloadsDir = File(context.getExternalFilesDir(null), "downloads")
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }
        val pdfFile = File(downloadsDir, fileName)

        viewModel.downloadPdf(fullUrl, pdfFile) { file ->
            openPdf(file)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->

        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Regresar a Historial",
//                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
                Text(
                    text = "Regresar",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.selectedAnalysis != null -> {
                    val analysis = uiState.selectedAnalysis!!

                    // Debug: Log analysis details
                    android.util.Log.d("AnalysisDetailScreen", "========== RENDERING ANALYSIS DETAILS ==========")
                    android.util.Log.d("AnalysisDetailScreen", "Analysis ID: ${analysis.id}")
                    android.util.Log.d("AnalysisDetailScreen", "Analysis Name: ${analysis.name}")
                    android.util.Log.d("AnalysisDetailScreen", "Download URL: ${analysis.downloadUrl}")
                    android.util.Log.d("AnalysisDetailScreen", "Download URL is null: ${analysis.downloadUrl == null}")

                    // Show toast to verify new version is installed
                    if (analysis.downloadUrl == null) {
                        android.widget.Toast.makeText(context, "DEBUG: downloadUrl es NULL", android.widget.Toast.LENGTH_SHORT).show()
                    }

                    Column(
                        modifier =
                            modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        // Summary Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                            colors =
                                CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                ),
                            elevation =
                                CardDefaults.cardElevation(
                                    defaultElevation = 2.dp,
                                ),
                        ) {
                            Column(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                // Header Row: Title, Date, and Download Icon
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
//                                    horizontalArrangement = Arrangement.Start,
//                                    verticalAlignment = Alignment.Top,
                                ) {
                                    Text(
                                        text = analysis.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = analysis.date,
                                        style = MaterialTheme.typography.bodyMedium,
//                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    if (analysis.downloadUrl != null) {
                                        android.util.Log.d("AnalysisDetailScreen", "Download button IS being rendered for URL: ${analysis.downloadUrl}")
                                        IconButton(onClick = {
                                            android.util.Log.d("AnalysisDetailScreen", "!!!!! DOWNLOAD BUTTON CLICKED !!!!!")
                                            android.util.Log.d("AnalysisDetailScreen", "About to call downloadAndOpenPdf with URL: ${analysis.downloadUrl}")
                                            android.widget.Toast.makeText(context, "Descargando PDF desde app...", android.widget.Toast.LENGTH_LONG).show()
                                            downloadAndOpenPdf(
                                                analysis.downloadUrl,
                                                "analisis_${analysis.id}.pdf"
                                            )
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.Download,
                                                contentDescription = "Download analysis",
                                                tint = MaterialTheme.colorScheme.primary,
                                            )
                                        }
                                    } else {
                                        android.util.Log.d("AnalysisDetailScreen", "Download button NOT rendered - downloadUrl is NULL")
                                    }
                                }
                            }
                        }

                        // Detail Sections
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color(0xFFE4F0F7),
                                        shape = RoundedCornerShape(12.dp),
                                    ).padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            if (!analysis.interpretations.isNullOrBlank()) {
                                DetailSection(
                                    header = "Interpretaciones",
                                    content = analysis.interpretations,
                                )
                            }
                            // Recommendations Section
                            if (!analysis.recommendations.isNullOrBlank()) {
                                DetailSection(
                                    header = "Recomendaciones",
                                    content = analysis.recommendations,
                                )
                            }
                        }
                    }
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "No se pudo cargar el análisis",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailSection(
    header: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = header,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
