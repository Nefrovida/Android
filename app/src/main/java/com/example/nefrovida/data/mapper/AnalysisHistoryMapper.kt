package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AnalysisHistoryDto
import com.example.nefrovida.domain.model.AnalysisHistory

fun AnalysisHistoryDto.toDomain(): AnalysisHistory =
    AnalysisHistory(
        id = id,
        name = name,
        date = formatDate(date),
        interpretations = interpretations,
        recommendations = recommendations,
        downloadUrl = downloadUrl,
    )

fun formatDate(date: String): String {
    val formattedDate = date.substring(0, 10)
    return formattedDate
}
