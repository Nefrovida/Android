package com.example.nefrovida.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.model.DayItem
import com.example.nefrovida.domain.repository.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

/**
 * ViewModel para la pantalla de citas
 */
@HiltViewModel
class AppointmentsViewModel @Inject constructor(
    private val repository: AppointmentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AppointmentsUiState>(AppointmentsUiState.Loading)
    val uiState: StateFlow<AppointmentsUiState> = _uiState.asStateFlow()

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    private val _days = MutableStateFlow<List<DayItem>>(emptyList())
    val days: StateFlow<List<DayItem>> = _days.asStateFlow()

    private val _selectedDay = MutableStateFlow<DayItem?>(null)
    val selectedDay: StateFlow<DayItem?> = _selectedDay.asStateFlow()

    // ID del doctor actual (puedes obtenerlo de SharedPreferences o del login)
    private var currentDoctorId: Int = 1 // TODO: Obtener del login

    init {
        initializeDays()
    }

    /**
     * Inicializa los días de la semana actual
     */
    private fun initializeDays() {
        val today = LocalDate.now()
        val daysList = mutableListOf<DayItem>()

        // Generar 7 días empezando desde hoy
        for (i in 0..6) {
            val date = today.plusDays(i.toLong())
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es", "ES"))
            val dayNumber = date.dayOfMonth
            val isSelected = i == 0 // El primer día está seleccionado por defecto

            daysList.add(DayItem(dayOfWeek, dayNumber, isSelected))
        }

        _days.value = daysList
        _selectedDay.value = daysList[0]

        // Cargar citas para el día seleccionado
        loadAppointmentsForSelectedDay()
    }

    /**
     * Maneja la selección de un día
     */
    fun onDaySelected(selectedDay: DayItem) {
        val updatedDays = _days.value.map { day ->
            day.copy(isSelected = day.dayNumber == selectedDay.dayNumber)
        }
        _days.value = updatedDays
        _selectedDay.value = selectedDay

        loadAppointmentsForSelectedDay()
    }

    /**
     * Carga las citas para el día seleccionado
     */
    private fun loadAppointmentsForSelectedDay() {
        viewModelScope.launch {
            _uiState.value = AppointmentsUiState.Loading

            val selectedDayValue = _selectedDay.value ?: return@launch
            val date = getCurrentYearMonth() + "-" + selectedDayValue.dayNumber.toString().padStart(2, '0')

            repository.getAppointments(currentDoctorId, date).fold(
                onSuccess = { appointments ->
                    _appointments.value = appointments
                    _uiState.value = if (appointments.isEmpty()) {
                        AppointmentsUiState.Empty
                    } else {
                        AppointmentsUiState.Success
                    }
                },
                onFailure = { error ->
                    _uiState.value = AppointmentsUiState.Error(
                        error.message ?: "Error al cargar las citas"
                    )
                }
            )
        }
    }

    /**
     * Recarga las citas
     */
    fun refresh() {
        loadAppointmentsForSelectedDay()
    }

    /**
     * Obtiene el año y mes actual en formato YYYY-MM
     */
    private fun getCurrentYearMonth(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        return today.format(formatter)
    }

    /**
     * Configura el ID del doctor actual
     */
    fun setDoctorId(doctorId: Int) {
        currentDoctorId = doctorId
        loadAppointmentsForSelectedDay()
    }
}

/**
 * Estados posibles de la UI
 */
sealed class AppointmentsUiState {
    object Loading : AppointmentsUiState()
    object Success : AppointmentsUiState()
    object Empty : AppointmentsUiState()
    data class Error(val message: String) : AppointmentsUiState()
}