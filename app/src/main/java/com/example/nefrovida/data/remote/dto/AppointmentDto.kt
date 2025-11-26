package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class AppointmentStatus {
    MISSED,
    CANCELED,
    FINISHED,
    PROGRAMMED,
}

enum class AppointmentTypes {
    PRESENCIAL,
    VIRTUAL,
}

data class AppointmentDto(
    @SerializedName("type") val type: String,
    @SerializedName("patient_appointment_id") val id: Int,
    @SerializedName("doctor_name") val name: String,
    @SerializedName("date_hour") val dateHour: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("link") val link: String?,
    @SerializedName("place") val place: String?,
    @SerializedName("appointment_status") val status: AppointmentStatus,
    @SerializedName("appointment_type") val appointmentType: AppointmentTypes,
    @SerializedName("appointment_name") val appointmentName: String?,
)
