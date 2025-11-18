package com.example.nefrovida.presentation.screens.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import com.example.nefrovida.domain.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// --- ViewModel ---

@HiltViewModel
class PatientAgendaViewModel @Inject constructor(
    private val repository: AppointmentRepository // Injects the repository
) : ViewModel() {

    // Logic for the LIST of appointments
    private val _listState = MutableStateFlow(AgendaListState())
    val listState: StateFlow<AgendaListState> = _listState.asStateFlow()

    // Logic for the DETAILS of appointments
    private val _detailState = MutableStateFlow(AgendaDetailState())
    val detailState: StateFlow<AgendaDetailState> = _detailState.asStateFlow()

    // Test token.
    // TODO: Replace this with a real login token
    private val FAKE_TOKEN = "Bearer TU_TOKEN_DE_PRUEBA_AQUI"

    init {
        // Loads the list of appointments as soon as the ViewModel is created
        loadAppointments()
    }

    fun loadAppointments() {
        viewModelScope.launch {
            _listState.value = AgendaListState(isLoading = true)
            try {
                _listState.value = AgendaListState(
                    // Calls the patient function in the repository
                    appointments = repository.getUserAppointments(FAKE_TOKEN)
                )
            } catch (e: Exception) {
                _listState.value = AgendaListState(error = e.message)
            }
        }
    }

    fun loadAppointmentDetails(id: String) {
        viewModelScope.launch {
            _detailState.value = AgendaDetailState(isLoading = true)
            try {
                _detailState.value = AgendaDetailState(
                    // Calls the patient function in the repository
                    appointment = repository.getAppointmentDetails(FAKE_TOKEN, id)
                )
            } catch (e: Exception) {
                _detailState.value = AgendaDetailState(error = e.message)
            }
        }
    }
}
