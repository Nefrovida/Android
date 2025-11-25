package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
            // to call a suspend function (that calls API or repository), in this case, loadAgendaList
            val scope = rememberCoroutineScope()
            // collectAsState changes userId from StateFlow to State to obtain its value
            val userId by viewModel.userId.collectAsState()

            WeeklyCalendarView(
                selectedDate = selectedDate,
                onDateSelected = { date ->
                    selectedDate = date
                    val formattedDate = date.toString()

                    if (userId.isNotEmpty()) {
                        scope.launch {
                            viewModel.loadAgendaList(formattedDate, userId)
                        }
                    }
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
                        when (appointment.appointmentType) {
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
                            Tipo: ${appointment.appointmentType}
                            $placeOrLink
                            
                            ¿Desea cancelar la cita?
                            """.trimIndent(),
                        confirmText = "Sí, cancelar",
                        dismissText = "No",
                        onConfirm = {
                            viewModel.cancelAppointment(appointment.id)
                            showDialog = false
                        },
                        onDismiss = { showDialog = false },
                    )
                }
            }
        }
    }
}
