package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.AnalysisHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class DownloadAnalysisPdfUseCase
    @Inject
    constructor(
        private val repository: AnalysisHistoryRepository,
    ) {
        operator fun invoke(url: String, destinationFile: File): Flow<Result<File>> =
            flow {
                try {
                    emit(Result.Loading)
                    android.util.Log.d("DownloadAnalysisPdfUseCase", "Starting download from: $url")
                    val success = repository.downloadPdf(url, destinationFile)
                    if (success) {
                        android.util.Log.d("DownloadAnalysisPdfUseCase", "Download successful")
                        emit(Result.Success(destinationFile))
                    } else {
                        android.util.Log.e("DownloadAnalysisPdfUseCase", "Download failed")
                        emit(Result.Error(Exception("Error al descargar el PDF. Verifica tu conexión e intenta nuevamente.")))
                    }
                } catch (e: Exception) {
                    android.util.Log.e("DownloadAnalysisPdfUseCase", "Download exception", e)
                    emit(Result.Error(e))
                }
            }
    }
