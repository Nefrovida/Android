package com.example.nefrovida.domain.model

data class Appointments (
    val appointmentId : Int,
    val name: String,
    val date: String,
    val time: String,
    val type: String,
    val duration: Int,
) {
    companion object {
        fun getMockData(): List<Appointments> =
            listOf(
                Appointments(
                    appointmentId = 1,
                    name = "Oliver Queen",
                    date = "2025-11-10",
                    time = "9:00",
                    type = "PRESENCIAL",
                    duration = 30,
                ),
                Appointments(
                appointmentId = 2,
                name = "John Doe",
                date = "2025-11-11",
                    time = "9:00",

                    type = "PRESENCIAL",
                duration = 45,
                ),
                Appointments(
                    appointmentId = 3,
                name = "Natalia Olivares",
                date = "2025-11-12",
                    time = "9:00",

                    type = "PRESENCIAL",
                duration = 60,
                ),
                Appointments(
                    appointmentId = 4,
                    name = "Adrian Marques",
                    date = "2025-11-12",
                    time = "9:00",

                    type = "PRESENCIAL",
                    duration = 20,
                ),
                Appointments(
                    appointmentId = 5,
                    name = "Santiago Alducin",
                    date = "2025-11-12",
                    time = "9:00",

                    type = "PRESENCIAL",
                    duration = 40,
                ),
                Appointments(
                    appointmentId = 6,
                    name = "Maria Centeno",
                    date = "2025-11-12",
                    time = "9:00",

                    type = "PRESENCIAL",
                    duration = 50,
                ),
                Appointments(
                    appointmentId = 7,
                    name = "Luis Ramirez",
                    date = "2025-11-12",
                    time = "9:00",

                    type = "PRESENCIAL",
                    duration = 30,
                ),
                Appointments(
                    appointmentId = 8,
                    name = "Mar Islas",
                    date = "2025-11-12",
                    time = "9:00",

                    type = "PRESENCIAL",
                    duration = 25,
                ),
                Appointments(
                    appointmentId = 9,
                    name = "Sandra Ruiz",
                    date = "2025-11-12",
                    time = "9:00",

                    type = "PRESENCIAL",
                    duration = 45,
                )
            )
    }
}