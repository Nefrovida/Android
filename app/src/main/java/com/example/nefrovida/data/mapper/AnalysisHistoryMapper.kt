package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AnalysisHistoryDto
import com.example.nefrovida.domain.model.AnalysisHistory

fun AnalysisHistoryDto.toDomain(): AnalysisHistory {
    return AnalysisHistory(
        id = id,
        name = name,
        specialty = specialty,
        doctorName = doctorName,
        date = date,
        recommendations = recommendations,
        treatment = treatment,
        downloadUrl = downloadUrl
    )
}
