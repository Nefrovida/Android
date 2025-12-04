package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateAppointmentRequest(
    @SerializedName("patientId")
    val patientId: String,
    @SerializedName("appointmentId")
    val appointmentId: Int,
    @SerializedName("dateHour")
    val dateHour: String,
    @SerializedName("appointmentType")
    val appointmentType: String,
)

data class CreateAppointmentResponse(
    @SerializedName("success")
    val success: Boolean,
)

