package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.home.components.AgendaList
import com.example.nefrovida.ui.atoms.SimpleIconButton
import com.example.nefrovida.ui.molecules.DatePickerDialog
import com.example.nefrovida.ui.molecules.Dialog
import com.example.nefrovida.ui.molecules.RescheduleFormCard
import kotlinx.coroutines.launch

@Composable
fun AgendaScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }
    var showReschedulePrompt by remember { mutableStateOf(false) }
    var showRescheduleForm by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val uiState by viewModel.uiState.collectAsState()
    val appointments = uiState.appointmentFilteredList
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (uiState.showCancelSuccess) {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar("Cita cancelada con éxito")
                viewModel.resetCancelSuccess()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { _ ->
        Column(
            modifier = modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                SimpleIconButton(
                    icon = Icons.Default.FilterAlt,
                    contentDescription = "Filtrar por día",
                    modifier = Modifier.padding(8.dp),
                    onClick = { showDatePicker = true },
                )
            }
            AgendaList(
                appointmentList = appointments ?: emptyList(),
                onCardClick = { appointment ->
                    viewModel.getAppointment(appointment.id)
                    showDialog = true
                },
            )
            if (showDialog) {
                uiState.selectedAppointment?.let { appointment ->
                    Dialog(
                        title = "Doctor: ${appointment.name}",
                        text =
                            """
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
                            showReschedulePrompt = true
                        },
                        onDismiss = { showDialog = false },
                    )
                }
            }
            if (showDatePicker) {
                DatePickerDialog(
                    onDismiss = { showDatePicker = false },
                    onDateSelected = { date ->
                        viewModel.loadAgendaList(date)
                    },
                )
            }
            if (showReschedulePrompt) {
                Dialog(
                    title = "Cita cancelada con exito",
                    text = "¿Deseas reagendar la cita?",
                    confirmText = "Sí",
                    dismissText = "No",
                    onConfirm = {
                        showReschedulePrompt = false
                        showRescheduleForm = true
                    },
                    onDismiss = { showReschedulePrompt = false },
                )
            }
            if (showRescheduleForm) {
                val appt = uiState.selectedAppointment
                RescheduleFormCard(
                    appointment = appt,
                    onCancel = { showRescheduleForm = false },
                    onReschedule = { reason, date, time ->
                        if (appt != null) {
                            viewModel.rescheduleAppointment(
                                id = appt.id,
                                reason = reason,
                                date = date,
                                time = time,
                            )
                        }
                        showRescheduleForm = false
                    },
                )
            }
        }
    }
}
