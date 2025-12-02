package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.repository.CatalogRepository
import javax.inject.Inject

class CreateAnalysisAppointmentUseCase
    @Inject
    constructor(
        private val catalogRepository: CatalogRepository,
    ) {
        suspend operator fun invoke(
            userId: String,
            analysisId: Int,
            analysisDate: String,
            place: String,
        ): Boolean =
            catalogRepository.createAnalysisAppointment(
                userId = userId,
                analysisId = analysisId,
                analysisDate = analysisDate,
                place = place,
            )
    }

