package com.example.nefrovida.presentation.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.usecase.GetAppointmentNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentNotesViewModel
    @Inject
    constructor(
        private val getAppointmentNotesUseCase: GetAppointmentNotesUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(AppointmentNotesUiState())
        val uiState = _uiState.asStateFlow()

        fun loadNotes() {
            viewModelScope.launch {
                try {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                    val result = getAppointmentNotesUseCase()
                    _uiState.value = _uiState.value.copy(appointmentNotes = result, isLoading = false)
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
                }
            }
        }
    }
