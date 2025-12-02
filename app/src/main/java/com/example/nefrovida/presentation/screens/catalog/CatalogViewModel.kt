package com.example.nefrovida.presentation.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.repository.UserPreferencesRepository
import com.example.nefrovida.domain.usecase.CreateAnalysisAppointmentUseCase
import com.example.nefrovida.domain.usecase.CreateAppointmentUseCase
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
            place: String,
            dateHour: String,
            duration: Int,
            doctorId: Int,
        ) {
            viewModelScope.launch {
                try {
                    val success =
                        createAppointmentUseCase(
                            patientId = _userId.value,
                            doctorId = doctorId,
                            dateHour = dateHour,
                            duration = duration,
                            appointmentType = appointmentType,
                            place = place,
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

        fun createAnalysisAppointment(
            analysisId: Int,
            analysisDate: String,
            place: String,
        ) {
            viewModelScope.launch {
                try {
                    val success =
                        createAnalysisAppointmentUseCase(
                            userId = _userId.value,
                            analysisId = analysisId,
                            analysisDate = analysisDate,
                            place = place,
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
