package com.example.nefrovida.domain.repository

import com.example.nefrovida.domain.model.AppointmentNotes

interface AppointmentNotesRepository {
    suspend fun getAppointmentNotesList(): List<AppointmentNotes>
}
