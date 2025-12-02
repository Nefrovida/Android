package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.CatalogRepository
import javax.inject.Inject

class CreateAppointmentUseCase
    @Inject
    constructor(
        private val catalogRepository: CatalogRepository,
    ) {
        suspend operator fun invoke(
            patientId: String,
            appointmentId: Int,
            dateHour: String,
            duration: Int,
            appointmentType: String,
            place: String,
        ): Boolean =
            catalogRepository.createAppointment(
                patientId = patientId,
                appointmentId = appointmentId,
                dateHour = dateHour,
                duration = duration,
                appointmentType = appointmentType,
                place = place,
            )
    }

