package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AnalysisHistoryDto
import com.example.nefrovida.domain.model.AnalysisHistory

fun AnalysisHistoryDto.toDomain(): AnalysisHistory =
    AnalysisHistory(
        id = id,
        name = name,
        date = date,
        recommendations = recommendations,
        downloadUrl = downloadUrl,
    )
