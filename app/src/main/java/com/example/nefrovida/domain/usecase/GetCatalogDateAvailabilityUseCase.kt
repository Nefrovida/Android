package com.example.nefrovida.domain.usecase

import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.CatalogRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCatalogDateAvailabilityUseCase
    @Inject
    constructor(
        private val repository: CatalogRepository,
    ) {
        operator fun invoke(
            doctorName: String,
            date: String,
        ): Flow<Result<List<String>>> =
            flow {
                try {
                    emit(Result.Loading)
                    val data = repository.getDateAvailability(doctorName, date)
                    emit(Result.Success(data))
                } catch (e: Exception) {
                    emit(Result.Error(e))
                }
            }
    }

