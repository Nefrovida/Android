package com.example.nefrovida.domain.model

import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.data.remote.dto.AppointmentTypes

data class Appointment (
    val id : Int,
    val name: String,
    val date: String,
    val time: String,
    val type: AppointmentTypes,
    val duration: Int,
    val link: String? = null,
    val place: String? = null,
    val status: AppointmentStatus,
    val appointmentName: String? = null,
) {}
