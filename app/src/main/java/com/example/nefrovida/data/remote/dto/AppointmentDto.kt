package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

enum class AppointmentStatus{
    MISSED,
    CANCELLED,
    FINISHED,
    PROGRAMMED
}

enum class AppointmentTypes{
    PRESENCIAL,
    VIRTUAL
}
data class AppointmentDto (
    @SerializedName("appointmentId") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("date_hour") val dateHour: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("link") val link: String?,
    @SerializedName("place") val place: String?,
    @SerializedName("appointment_status") val status: AppointmentStatus,
    @SerializedName("appointment_type") val type: AppointmentTypes,

    ){

}