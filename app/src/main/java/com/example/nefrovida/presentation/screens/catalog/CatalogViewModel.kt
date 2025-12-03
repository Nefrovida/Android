package com.example.nefrovida.presentation.screens.catalog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.repository.UserPreferencesRepository
import com.example.nefrovida.domain.usecase.CreateAnalysisAppointmentUseCase
import com.example.nefrovida.domain.usecase.CreateAppointmentUseCase
import com.example.nefrovida.domain.usecase.GetCatalogAnalysisDateAvailabilityUseCase
import com.example.nefrovida.domain.usecase.GetCatalogDateAvailabilityUseCase
import com.example.nefrovida.domain.usecase.GetCatalogListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel
    @Inject
    constructor(
        private val getCatalogListUseCase: GetCatalogListUseCase,
        private val createAppointmentUseCase: CreateAppointmentUseCase,
        private val createAnalysisAppointmentUseCase: CreateAnalysisAppointmentUseCase,
        private val getCatalogDateAvailabilityUseCase: GetCatalogDateAvailabilityUseCase,
        private val getCatalogAnalysisDateAvailabilityUseCase: GetCatalogAnalysisDateAvailabilityUseCase,
        private val userPreferencesRepository: UserPreferencesRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CatalogUIState())
        val uiState = _uiState.asStateFlow()

        private val _userId = MutableStateFlow("")
        val userId = _userId.asStateFlow()

        init {
            viewModelScope.launch {
                userPreferencesRepository.userIdFlow.collect { id ->
                    _userId.value = id ?: ""
                }
            }
            loadCatalog()
        }

        fun loadCatalog() {
            viewModelScope.launch {
                _uiState.value = uiState.value.copy(isLoading = true)
                try {
                    val result = getCatalogListUseCase()
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            analysis = result.analysis,
                            appointments = result.appointments,
                            error = null,
                        )
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = e.message,
                        )
                }
            }
        }

        fun createAppointment(
            appointmentType: String,
            dateHour: String,
            appointmentId: Int,
        ) {
            viewModelScope.launch {
                try {
                    val success =
                        createAppointmentUseCase(
                            patientId = _userId.value,
                            appointmentId = appointmentId,
                            dateHour = dateHour,
                            appointmentType = appointmentType,
                        )
                    _uiState.value =
                        _uiState.value.copy(
                            showCreateSuccess = success,
                            showCreateError = !success,
                        )
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            showCreateSuccess = false,
                            showCreateError = true,
                        )
                }
            }
        }

        suspend fun getDateAvailability(
            date: String,
            appointmentId: Int,
        ): List<String> {
            var result: List<String> = emptyList()

            getCatalogDateAvailabilityUseCase(date, appointmentId).collect { state ->
                when (state) {
                    is Result.Loading -> {
                        Log.d("CatalogVM", "Get Availability is loading")
                    }
                    is Result.Success -> {
                        Log.d("CatalogVM", "Get Availability List is successful: ${state.data}")
                        result = state.data
                    }
                    is Result.Error -> {
                        Log.e("CatalogVM", "Get Availability error: ${state.exception.message}")
                        result = emptyList()
                    }
                }
            }

            return result
        }

        suspend fun getAnalysisDateAvailability(
            analysisName: String,
            date: String,
        ): List<String> {
            var result: List<String> = emptyList()

            getCatalogAnalysisDateAvailabilityUseCase(analysisName, date).collect { state ->
                when (state) {
                    is Result.Loading -> {
                        Log.d("CatalogVM", "Get Analysis Availability is loading")
                    }
                    is Result.Success -> {
                        Log.d("CatalogVM", "Get Analysis Availability List is successful: ${state.data}")
                        result = state.data
                    }
                    is Result.Error -> {
                        Log.e("CatalogVM", "Get Analysis Availability error: ${state.exception.message}")
                        result = emptyList()
                    }
                }
            }

            return result
        }

        fun createAnalysisAppointment(
            analysisId: Int,
            analysisDate: String,
        ) {
            viewModelScope.launch {
                try {
                    val success =
                        createAnalysisAppointmentUseCase(
                            userId = _userId.value,
                            analysisId = analysisId,
                            analysisDate = analysisDate,
                        )
                    _uiState.value =
                        _uiState.value.copy(
                            showCreateSuccess = success,
                            showCreateError = !success,
                        )
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            showCreateSuccess = false,
                            showCreateError = true,
                        )
                }
            }
        }

        fun resetCreateSuccess() {
            _uiState.value = _uiState.value.copy(showCreateSuccess = false)
        }

        fun resetCreateError() {
            _uiState.value = _uiState.value.copy(showCreateError = false)
        }
    }
