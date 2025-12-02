package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AppointmentNotesDto
import com.example.nefrovida.data.remote.dto.NotesDto
import com.example.nefrovida.domain.model.AppointmentNotes
import com.example.nefrovida.domain.model.Notes

fun AppointmentNotesDto.toDomain(): AppointmentNotes =
    AppointmentNotes(
        appointmentName = name,
        date = formatDate(date),
        notes = noteList.toDomain(),
    )

fun List<NotesDto>.toDomain(): List<Notes> = map { it.toDomain() }

fun NotesDto.toDomain(): Notes =
    Notes(
        content = content,
        generalNotes = generalNotes,
        ailments = ailments,
        visibility = visibility,
        prescription = prescription,
    )
