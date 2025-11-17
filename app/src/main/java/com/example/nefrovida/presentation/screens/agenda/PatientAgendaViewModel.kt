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

// Estado para la lista de citas
data class AgendaListState(
    val appointments: List<AppointmentDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// Estado para la pantalla de detalles
data class AgendaDetailState(
    val appointment: AppointmentDetailDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PatientAgendaViewModel @Inject constructor(
    private val repository: AppointmentRepository // Inyecta el Repositorio limpio
) : ViewModel() {

    // --- Lógica para la LISTA de citas ---
    private val _listState = MutableStateFlow(AgendaListState())
    val listState: StateFlow<AgendaListState> = _listState.asStateFlow()

    // --- Lógica para los DETALLES de citas ---
    private val _detailState = MutableStateFlow(AgendaDetailState())
    val detailState: StateFlow<AgendaDetailState> = _detailState.asStateFlow()

    // Token de prueba.
    private val FAKE_TOKEN = "Bearer TU_TOKEN_DE_PRUEBA_AQUI"

    init {
        loadAppointments()
    }

    fun loadAppointments() {
        viewModelScope.launch {
            _listState.value = AgendaListState(isLoading = true)
            try {
                _listState.value = AgendaListState(
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
                    appointment = repository.getAppointmentDetails(FAKE_TOKEN, id)
                )
            } catch (e: Exception) {
                _detailState.value = AgendaDetailState(error = e.message)
            }
        }
    }
}