package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.AppointmentApi
import com.example.nefrovida.domain.model.AppointmentNotes
import com.example.nefrovida.domain.repository.AppointmentNotesRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

class AppointmentNotesRepositoryImpl
    @Inject
    constructor(
        private val api: AppointmentApi,
    ) : AppointmentNotesRepository {
        override suspend fun getAppointmentNotesList(): List<AppointmentNotes> {
            val response = api.getNotes()
            return response.map { it.toDomain() }
        }
    }
