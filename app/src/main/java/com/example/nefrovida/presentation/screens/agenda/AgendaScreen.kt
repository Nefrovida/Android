package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.Arrangement
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.data.remote.dto.AppointmentTypes
import com.example.nefrovida.presentation.screens.home.components.AgendaList
import com.example.nefrovida.ui.atoms.SimpleIconButton
import com.example.nefrovida.ui.molecules.DatePickerDialog
import com.example.nefrovida.ui.molecules.Dialog
import com.example.nefrovida.ui.molecules.RescheduleFormCard
import kotlinx.coroutines.launch
import com.example.nefrovida.ui.molecules.Dialog
import com.example.nefrovida.ui.molecules.WeeklyCalendarView
import kotlinx.coroutines.launch
import java.time.LocalDate

@Suppress("ktlint:standard:function-naming")
@Composable
fun AgendaScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    var showDialog by remember { mutableStateOf(false) }
    var showAgendaList by remember { mutableStateOf(true) }
    var showReschedulePrompt by remember { mutableStateOf(false) }
    var showRescheduleForm by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val uiState by viewModel.uiState.collectAsState()
    val appointments = uiState.appointmentFilteredList
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

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
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(),
        ) {
            WeeklyCalendarView(
                selectedDate = selectedDate,
                onDateSelected = { date ->
                    selectedDate = date
                    val formattedDate = date.toString()
                    viewModel.loadAgendaList(formattedDate)
                },
                modifier = Modifier.padding(8.dp),
            )

            if (appointments.isNullOrEmpty()) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "No hay citas para este día.",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp,
                        color = Color.Gray,
                    )
                }
            } else {
                AgendaList(
                    appointmentList = appointments,
                    onCardClick = { appointment ->
                        viewModel.getAppointment(appointment.id)
                        showDialog = true
                    },
                )
            }

            if (showDialog) {
                uiState.selectedAppointment?.let { appointment ->

                    val placeOrLink =
                        when (appointment.type) {
                            AppointmentTypes.VIRTUAL -> {
                                val link = appointment.link
                                if (!link.isNullOrBlank()) "Link: $link" else ""
                            }
                            AppointmentTypes.PRESENCIAL -> {
                                val place = appointment.place
                                if (!place.isNullOrBlank()) "Lugar: $place" else ""
                            }
                            else -> ""
                        }

                    Dialog(
                        title = "Doctor: ${appointment.name}",
                        text =
                            """
                            ${appointment.appointmentName}
                            Fecha: ${appointment.date}
                            Hora: ${appointment.time}
                            Tipo: ${appointment.type}
                            $placeOrLink
                            
                            ¿Desea cancelar la cita?
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
                        showAgendaList = false
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
                    getDateAvailability = { name, date -> viewModel.getDateAvailability(name, date) },
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
                        showAgendaList = true
                    },
                )
            }
        }
    }
}
