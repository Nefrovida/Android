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
) {
    companion object {
        fun getMockData(): List<Appointment> =
            listOf(
                Appointment(
                    id = 1,
                    name = "Oliver Queen",
                    date = "2025-11-10",
                    type = AppointmentTypes.PRESENCIAL,
                    duration = 30,
                    time = "09:00",
                    status = AppointmentStatus.PROGRAMMED,

                ),
            )
    }
}
