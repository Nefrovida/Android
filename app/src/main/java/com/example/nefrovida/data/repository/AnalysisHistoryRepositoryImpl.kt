package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.AnalysisHistoryApi
import com.example.nefrovida.domain.model.AnalysisHistory
import com.example.nefrovida.domain.repository.AnalysisHistoryRepository
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalysisHistoryRepositoryImpl
    @Inject
    constructor(
        private val api: AnalysisHistoryApi,
    ) : AnalysisHistoryRepository {
        override suspend fun getAnalysisHistory(): List<AnalysisHistory> {
            val response = api.getAnalysisHistory()
            return response.map { it.toDomain() }
        }

        override suspend fun getAnalysisById(id: Int): AnalysisHistory {
            val response = api.getAnalysisById(id)
            return response.toDomain()
        }

        override suspend fun downloadPdf(url: String, destinationFile: File): Boolean {
            return try {
                android.util.Log.d("AnalysisDownload", "========== STARTING PDF DOWNLOAD ==========")
                android.util.Log.d("AnalysisDownload", "Downloading from URL: $url")

                val response = api.downloadFile(url)

                // Check content type
                val contentType = response.contentType()
                android.util.Log.d("AnalysisDownload", "Content-Type: $contentType")
                android.util.Log.d("AnalysisDownload", "Content-Length: ${response.contentLength()} bytes")

                // Read the response into a byte array first so we can inspect it
                val bytes = response.bytes()
                android.util.Log.d("AnalysisDownload", "Downloaded ${bytes.size} bytes")

                // Check if it's actually a PDF by looking at the first few bytes
                if (bytes.size >= 4) {
                    val header = String(bytes.sliceArray(0 until minOf(4, bytes.size)))
                    android.util.Log.d("AnalysisDownload", "File header: $header")

                    if (header != "%PDF") {
                        // Not a PDF, probably an error response - read the whole thing
                        val errorBody = String(bytes)
                        android.util.Log.e("AnalysisDownload", "Server returned non-PDF response!")
                        android.util.Log.e("AnalysisDownload", "Full response body: $errorBody")
                        throw Exception("Error del servidor: $errorBody")
                    }
                }

                // Write bytes to file
                FileOutputStream(destinationFile).use { output ->
                    output.write(bytes)
                }

                android.util.Log.d("AnalysisDownload", "File saved successfully to: ${destinationFile.absolutePath}")
                android.util.Log.d("AnalysisDownload", "File size: ${destinationFile.length()} bytes")
                android.util.Log.d("AnalysisDownload", "========== PDF DOWNLOAD COMPLETE ==========")

                true
            } catch (e: Exception) {
                android.util.Log.e("AnalysisDownload", "========== PDF DOWNLOAD FAILED ==========")
                android.util.Log.e("AnalysisDownload", "Error downloading PDF: ${e.message}", e)
                e.printStackTrace()
                false
            }
        }
    }
