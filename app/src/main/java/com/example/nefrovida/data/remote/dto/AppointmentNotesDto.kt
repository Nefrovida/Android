package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AppointmentNotesDto(
    @SerializedName("appointmentName") val name: String,
    @SerializedName("dateHour") val date: String,
    @SerializedName("notes") val noteList: List<NotesDto>,
)

data class NotesDto(
    @SerializedName("content") val content: String,
    @SerializedName("generalNotes") val generalNotes: String,
    @SerializedName("ailments") val ailments: String,
    @SerializedName("visibility") val visibility: Boolean,
    @SerializedName("prescription") val prescription: String,
)
