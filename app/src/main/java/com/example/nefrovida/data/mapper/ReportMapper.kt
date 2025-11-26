package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AnalysisInfo
import com.example.nefrovida.data.remote.dto.PatientAnalysisDetail
import com.example.nefrovida.data.remote.dto.ResultResponse
import com.example.nefrovida.domain.model.Analysis
import com.example.nefrovida.domain.model.AnalysisStatus
import com.example.nefrovida.domain.model.PatientAnalysis
import com.example.nefrovida.domain.model.Report

fun ResultResponse.toDomain() =
    Report(
        resultId = resultId,
        patientAnalysisId = patientAnalysisId,
        date = date,
        path = path,
        interpretation = interpretation,
        patientAnalysis = patientAnalysis.toDomain(),
    )

fun PatientAnalysisDetail.toDomain() =
    PatientAnalysis(
        type,
        patientAnalysisId,
        analysisName,
        analysisDate,
        resultsDate,
        place,
        duration,
        analysisStatus.toDomain(),
        analysis?.toDomain(),
    )

fun AnalysisInfo.toDomain() =
    Analysis(
        analysisId,
        name,
        description,
    )

fun com.example.nefrovida.data.remote.dto.AnalysisStatus.toDomain(): AnalysisStatus =
    when (this) {
        com.example.nefrovida.data.remote.dto.AnalysisStatus.LAB -> AnalysisStatus.LAB
        com.example.nefrovida.data.remote.dto.AnalysisStatus.PENDING -> AnalysisStatus.PENDING
        com.example.nefrovida.data.remote.dto.AnalysisStatus.SENT -> AnalysisStatus.SENT
        com.example.nefrovida.data.remote.dto.AnalysisStatus.REQUESTED -> AnalysisStatus.REQUESTED
    }
