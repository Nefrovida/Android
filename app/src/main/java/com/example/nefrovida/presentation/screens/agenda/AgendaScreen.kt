package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.home.components.AgendaList
import com.example.nefrovida.ui.molecules.Dialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.ui.atoms.SimpleIconButton

@Composable
fun AgendaScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val uiState by viewModel.uiState.collectAsState()
    val appointments = uiState.appointmentFilteredList

    Scaffold { _ ->
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                SimpleIconButton(
                    icon = Icons.Default.FilterAlt,
                    contentDescription = "Filtrar por día",
                    modifier = Modifier.padding(8.dp),
                    onClick = { showDatePicker = true }
                )
            }
            AgendaList(
                appointmentList = appointments ?: emptyList(),
                onCardClick = { appointment ->
                    viewModel.getAppointment(appointment.id)
                    showDialog = true
                }
            )
            if (showDialog) {
                uiState.selectedAppointment?.let { appointment ->
                    Dialog(
                        title = "Doctor: ${appointment.name}",
                        text = """
                            Fecha: ${appointment.date}
                            Hora: ${appointment.time}
                            ${appointment.type}

                            ¿Deseas cancelar esta cita?
                        """.trimIndent(),
                        confirmText = "Sí, cancelar",
                        dismissText = "No",
                        onConfirm = {
                            viewModel.cancelAppointment(appointment.id)
                            showDialog = false
                        },
                        onDismiss = { showDialog = false }
                    )
                }
            }
            if (showDatePicker) {
                androidx.compose.ui.window.Dialog(onDismissRequest = { showDatePicker = false }) {
                    androidx.compose.material3.Surface(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium,
                        tonalElevation = 6.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            DatePicker(
                                state = datePickerState,
                                colors = DatePickerDefaults.colors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                                    headlineContentColor = MaterialTheme.colorScheme.onSurface,
                                    weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                                    subheadContentColor = MaterialTheme.colorScheme.onSurface,
                                    yearContentColor = MaterialTheme.colorScheme.onSurface,
                                    selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
                                    dayContentColor = MaterialTheme.colorScheme.onSurface,
                                    selectedDayContentColor = MaterialTheme.colorScheme.onSurface,
                                    todayContentColor = MaterialTheme.colorScheme.onSurface,
                                    todayDateBorderColor = MaterialTheme.colorScheme.onSurface,
                                    selectedDayContainerColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                            androidx.compose.material3.Divider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = { showDatePicker = false }) {
                                    Text(
                                        "Cancelar",
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                TextButton(onClick = {
                                    val selectedDate = datePickerState.selectedDateMillis
                                    if (selectedDate != null) {
                                        val formattedDate = java.text.SimpleDateFormat("yyyy-MM-dd")
                                            .format(java.util.Date(selectedDate))
                                        viewModel.loadAgendaList(formattedDate)
                                    }
                                    showDatePicker = false
                                }) {
                                    Text(
                                        "Aceptar",
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

