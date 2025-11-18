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


// --- El ViewModel ---

@HiltViewModel
class PatientAgendaViewModel @Inject constructor(
    private val repository: AppointmentRepository // Inyecta el Repositorio
) : ViewModel() {

    // --- Lógica para la LISTA de citas ---
    private val _listState = MutableStateFlow(AgendaListState())
    val listState: StateFlow<AgendaListState> = _listState.asStateFlow()

    // --- Lógica para los DETALLES de citas ---
    private val _detailState = MutableStateFlow(AgendaDetailState())
    val detailState: StateFlow<AgendaDetailState> = _detailState.asStateFlow()

    // Token de prueba.
    // TODO: Reemplaza esto con un token de login real
    private val FAKE_TOKEN = "Bearer TU_TOKEN_DE_PRUEBA_AQUI"

    init {
        // Carga la lista de citas tan pronto se crea el ViewModel
        loadAppointments()
    }

    fun loadAppointments() {
        viewModelScope.launch {
            _listState.value = AgendaListState(isLoading = true)
            try {
                _listState.value = AgendaListState(
                    // Llama a la función del paciente en el repo
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
                    // Llama a la función del paciente en el repo
                    appointment = repository.getAppointmentDetails(FAKE_TOKEN, id)
                )
            } catch (e: Exception) {
                _detailState.value = AgendaDetailState(error = e.message)
            }
        }
    }
}