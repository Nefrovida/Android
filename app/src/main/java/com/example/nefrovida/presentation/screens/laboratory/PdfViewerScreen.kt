package com.example.nefrovida.presentation.screens.laboratory

import android.content.ContentValues
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView
import com.example.nefrovida.presentation.common.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun PdfViewerScreen(
    relativePath: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    var localFile by remember { mutableStateOf<File?>(null) }
    var isLoading by remember { mutableStateOf(true) }


    LaunchedEffect(relativePath) {
        withContext(Dispatchers.IO) {
            try {

                val fullUrl = "${Constants.BASE_URL}$relativePath"
                val url = URL(fullUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()


                val inputStream = connection.inputStream
                val file = File(context.cacheDir, "temp_report.pdf")
                val outputStream = FileOutputStream(file)

                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
                localFile = file
                isLoading = false
            } catch (e: Exception) {
                e.printStackTrace()
                isLoading = false
            }
        }
    }


    fun saveToDownloads() {
        scope.launch(Dispatchers.IO) {
            localFile?.let { file ->
                val resolver = context.contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, "Reporte_Medico_${System.currentTimeMillis()}.pdf")
                    put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                }

                val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

                uri?.let { targetUri ->
                    resolver.openOutputStream(targetUri)?.use { output ->
                        file.inputStream().use { input ->
                            input.copyTo(output)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "PDF Guardado en Descargas", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!isLoading && localFile != null) {
                FloatingActionButton(onClick = { saveToDownloads() }) {
                    Icon(Icons.Default.Save, contentDescription = "Guardar")
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (localFile != null) {
                // 3. El Visor de la librería
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        PDFView(ctx, null)
                    },
                    update = { pdfView ->
                        pdfView.fromFile(localFile)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .load()
                    }
                )
            } else {
                androidx.compose.material3.Text("Error al cargar PDF")
            }
        }
    }
}