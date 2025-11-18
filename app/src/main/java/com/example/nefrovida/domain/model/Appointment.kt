package com.example.nefrovida.domain.model

import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.data.remote.dto.AppointmentTypes
import java.util.Date

data class Appointment(
    val id: Int,
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
        // This function creates and returns a list of sample appointments.
        fun getMockData(): List<Appointment> =
            listOf(
                Appointment(
                    id = 1,
                    name = "Dr. García",
                    date = "2023-11-15",
                    time = "10:00",
                    type = AppointmentTypes.PRESENCIAL,
                    duration = 30,
                    status = AppointmentStatus.SCHEDULED,
                    appointmentName = "Consulta General",
                ),
                Appointment(
                    id = 2,
                    name = "Dr. Martínez",
                    date = "2023-11-16",
                    time = "14:00",
                    type = AppointmentTypes.PRESENCIAL,
                    duration = 15,
                    status = AppointmentStatus.SCHEDULED,
                    appointmentName = "Análisis de Sangre",
                ),
                Appointment(
                    id = 3,
                    name = "Dr. López",
                    date = "2023-11-17",
                    time = "08:00",
                    type = AppointmentTypes.PRESENCIAL,
                    duration = 240,
                    status = AppointmentStatus.SCHEDULED,
                    appointmentName = "Diálisis",
                ),
                Appointment(
                    id = 4,
                    name = "Lic. Rodríguez",
                    date = "2023-11-18",
                    time = "16:00",
                    type = AppointmentTypes.PRESENCIAL,
                    duration = 45,
                    status = AppointmentStatus.SCHEDULED,
                    appointmentName = "Revisión Nutricional",
                ),
            )
    }
}
