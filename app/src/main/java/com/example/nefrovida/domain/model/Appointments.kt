package com.example.nefrovida.domain.model

data class Appointments (
    val appointmentId : String,
    val name: String,
    val date: String,
    val type: String,
    val duration: Int,
) {
    companion object {
        fun getMockData(): List<Appointments> =
            listOf(
                Appointments(
                    appointmentId = "5t5vwav4t5v54",
                    name = "Oliver Queen",
                    date = "2025-11-10",
                    type = "PRESENCIAL",
                    duration = 30,
                ),
                Appointments(
                appointmentId = "98y4b845ynwbp",
                name = "John Doe",
                date = "2025-11-11",
                type = "PRESENCIAL",
                duration = 45,
                ),
                Appointments(
                    appointmentId = "eoqioi4w34",
                name = "Natalia Olivares",
                date = "2025-11-12",
                type = "PRESENCIAL",
                duration = 60,
                ),
                Appointments(
                    appointmentId = "b1d5t6rbtrb1",
                    name = "Adrian Marques",
                    date = "2025-11-12",
                    type = "PRESENCIAL",
                    duration = 20,
                ),
                Appointments(
                    appointmentId = "s56841b98r9",
                    name = "Santiago Alducin",
                    date = "2025-11-12",
                    type = "PRESENCIAL",
                    duration = 40,
                ),
                Appointments(
                    appointmentId = "6rw4gv9q8rg4",
                    name = "Maria Centeno",
                    date = "2025-11-12",
                    type = "PRESENCIAL",
                    duration = 50,
                ),
                Appointments(
                    appointmentId = "va6r84t98t",
                    name = "Luis Ramirez",
                    date = "2025-11-12",
                    type = "PRESENCIAL",
                    duration = 30,
                ),
                Appointments(
                    appointmentId = "wr98498wrvb19r",
                    name = "Mar Islas",
                    date = "2025-11-12",
                    type = "PRESENCIAL",
                    duration = 25,
                ),
                Appointments(
                    appointmentId = "5f6q648erf",
                    name = "Sandra Ruiz",
                    date = "2025-11-12",
                    type = "PRESENCIAL",
                    duration = 45,
                )
            )
    }
}