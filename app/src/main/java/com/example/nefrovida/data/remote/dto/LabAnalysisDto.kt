package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

enum class AnalysisTypes {
    SENT,
    PENDING,
    LAB,
    REQUESTED
}

data class LabAnalysisDto(
    @SerializedName("patient_analysis_id") val patient_analysis_id: Int,
    @SerializedName("analysis_date") val analysis_date: Date,
    @SerializedName("results_date") val results_date: Date,
    @SerializedName("analysis_status") val analysis_status: AnalysisTypes,
    @SerializedName("patient") val patient: PatientDto
) {
    data class PatientDto(
        @SerializedName("user") val user: UserDto
    )

    data class UserDto(
        @SerializedName("name") val name: String,
        @SerializedName("parent_last_name") val parent_last_name: String,
        @SerializedName("maternal_last_name") val maternal_last_name: String
    )
}