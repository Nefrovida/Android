// en presentation/screens/agenda/AgendaViewModel.kt
package com.example.nefrovida.presentation.screens.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.repository.AppointmentRepository 
import com.example.nefrovida.data.network.dto.AppointmentDetailDto
import com.example.nefrovida.data.network.dto.AppointmentDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AgendaListState(
    val appointments: List<AppointmentDto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class AgendaDetailState(
    val appointment: AppointmentDetailDto? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _listState = MutableStateFlow(AgendaListState())
    val listState: StateFlow<AgendaListState> = _listState.asStateFlow()

    private val _detailState = MutableStateFlow(AgendaDetailState())
    val detailState: StateFlow<AgendaDetailState> = _detailState.asStateFlow()

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
