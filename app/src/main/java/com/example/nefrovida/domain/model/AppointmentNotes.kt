package com.example.nefrovida.domain.model

data class AppointmentNotes(
    val appointmentName: String,
    val date: String,
    val notes: List<Notes>,
)

data class Notes(
    val content: String,
    val generalNotes: String,
    val ailments: String,
    val visibility: Boolean,
    val prescription: String,
)
