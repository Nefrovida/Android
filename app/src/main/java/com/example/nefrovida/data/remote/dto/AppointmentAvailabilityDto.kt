package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AppointmentAvailabilityDto(
    @SerializedName("patient_appointment_id")
    val patientAppointmentId: Int,
    @SerializedName("patient_id")
    val patientId: String,
    @SerializedName("appointment_id")
    val appointmentId: Int,
    @SerializedName("date_hour")
    val dateHour: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("appointment_type")
    val appointmentType: String,
    @SerializedName("link")
    val link: String?,
    @SerializedName("place")
    val place: String?,
    @SerializedName("appointment_status")
    val appointmentStatus: String,
    @SerializedName("patient_name")
    val patientName: String,
    @SerializedName("patient_parent_last_name")
    val patientParentLastName: String,
    @SerializedName("patient_maternal_last_name")
    val patientMaternalLastName: String,
)

