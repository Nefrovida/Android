package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateAppointmentRequest(
    @SerializedName("patientId")
    val patientId: String,
    @SerializedName("appointmentId")
    val appointmentId: Int,
    @SerializedName("dateHour")
    val dateHour: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("appointmentType")
    val appointmentType: String,
    @SerializedName("place")
    val place: String,
)

data class CreateAppointmentResponse(
    @SerializedName("success")
    val success: Boolean,
)

