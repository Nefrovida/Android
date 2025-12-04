package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.AppointmentNotesRepository
import jakarta.inject.Inject

class GetAppointmentNotesUseCase
    @Inject
    constructor(
        private val repository: AppointmentNotesRepository,
    ) {
        suspend operator fun invoke() = repository.getAppointmentNotesList()
    }
