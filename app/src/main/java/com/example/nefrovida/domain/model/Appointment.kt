package com.example.nefrovida.domain.model

import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.data.remote.dto.AppointmentTypes

data class Appointment (
    val id : String,
    val name: String,
    val date: String,
    val time: String,
    val type: AppointmentTypes,
    val duration: Int,
    val link: String? = null,
    val place: String? = null,
    val status: AppointmentStatus
) {/*
    companion object {
        fun getMockData(): List<Appointment> =
            listOf(
                Appointment(
                    id = 1,
                    name = "Oliver Queen",
                    date = "2025-11-10",
                    time = "9:00",
                    type = AppointmentTypes.PRESENCIAL,
                    duration = 30,
                    status = AppointmentStatus.PROGRAMMED,
                ),
                Appointment(
                id = 2,
                name = "John Doe",
                date = "2025-11-11",
                    time = "9:00",

                    type = AppointmentTypes.PRESENCIAL,
                duration = 45,
                    status = AppointmentStatus.PROGRAMMED,

                    ),
                Appointment(
                    id = 3,
                name = "Natalia Olivares",
                date = "2025-11-12",
                    time = "9:00",

                    type = AppointmentTypes.PRESENCIAL,
                duration = 60,
                    status = AppointmentStatus.PROGRAMMED,

                    ),
                Appointment(
                    id = 4,
                    name = "Adrian Marques",
                    date = "2025-11-12",
                    time = "9:00",

                    type = AppointmentTypes.PRESENCIAL,
                    duration = 20,
                    status = AppointmentStatus.PROGRAMMED,

                    ),
                Appointment(
                    id = 5,
                    name = "Santiago Alducin",
                    date = "2025-11-12",
                    time = "9:00",

                    type = AppointmentTypes.PRESENCIAL,
                    duration = 40,
                    status = AppointmentStatus.PROGRAMMED,

                    ),
                Appointment(
                    id = 6,
                    name = "Maria Centeno",
                    date = "2025-11-12",
                    time = "9:00",

                    type = AppointmentTypes.PRESENCIAL,
                    duration = 50,
                    status = AppointmentStatus.PROGRAMMED,

                    ),
                Appointment(
                    id = 7,
                    name = "Luis Ramirez",
                    date = "2025-11-12",
                    time = "9:00",

                    type = AppointmentTypes.PRESENCIAL,
                    duration = 30,
                    status = AppointmentStatus.PROGRAMMED,

                    ),
                Appointment(
                    id = 8,
                    name = "Mar Islas",
                    date = "2025-11-12",
                    time = "9:00",

                    type = AppointmentTypes.PRESENCIAL,
                    duration = 25,
                    status = AppointmentStatus.PROGRAMMED,

                    ),
                Appointment(
                    id = 9,
                    name = "Sandra Ruiz",
                    date = "2025-11-12",
                    time = "9:00",

                    type = AppointmentTypes.PRESENCIAL,
                    duration = 45,
                    status = AppointmentStatus.PROGRAMMED,

                    )
            )
    }*/
}