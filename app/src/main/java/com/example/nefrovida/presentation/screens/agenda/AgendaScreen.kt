package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.data.remote.dto.AppointmentTypes
import com.example.nefrovida.domain.model.AgendaItem
import com.example.nefrovida.domain.model.AnalysisStatus
import com.example.nefrovida.presentation.utils.formatDatePretty
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

    val appointments = uiState.appointmentFilteredList?.appointments ?: emptyList()
    val analysis = uiState.appointmentFilteredList?.analysis ?: emptyList()
    val unifiedList: List<AgendaItem> =
        appointments.map { AgendaItem.AppointmentItem(it) } +
            analysis.map { AgendaItem.AnalysisItem(it) }

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
            val scope = rememberCoroutineScope()
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

            if (unifiedList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "No hay citas ni análisis para este día.",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp,
                        color = Color.Gray,
                    )
                }
            } else {
                AgendaUnifiedList(
                    items = unifiedList,
                    onAppointmentClick = { appointment ->
                        viewModel.getAppointment(appointment.id)
                        viewModel.selectItem(AgendaItem.AppointmentItem(appointment))
                        showDialog = true
                    },
                    onAnalysisClick = { analysis ->
                        viewModel.getAnalysis(analysis.patientAnalysisId)
                        viewModel.selectItem(AgendaItem.AnalysisItem(analysis))
                        showDialog = true
                    },
                )
            }

            if (showDialog) {
                when (val item = uiState.selectedItem) {
                    is AgendaItem.AppointmentItem -> {
                        uiState.selectedAppointment?.let { appointment ->

                            val placeOrLink =
                                when (appointment.appointmentType) {
                                    AppointmentTypes.VIRTUAL ->
                                        appointment.link?.let { "Link: $it" }.orEmpty()

                                    AppointmentTypes.PRESENCIAL ->
                                        appointment.place?.let { "Lugar: $it" }.orEmpty()

                                    else -> ""
                                }
                            val status =
                                when (appointment.status) {
                                    AppointmentStatus.REQUESTED ->
                                        appointment.status?.let { "POR CONFIRMAR" }.orEmpty()

                                    AppointmentStatus.PROGRAMMED ->
                                        appointment.status?.let { "CONFIRMADA" }.orEmpty()

                                    else -> ""
                                }

                            Dialog(
                                title = "Doctor: ${appointment.name}",
                                text = {
                                    Text(
                                        "${appointment.appointmentName}",
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                    Text("Fecha: ${appointment.date}")
                                    Text("Hora: ${appointment.time}")
                                    Text("Tipo: ${appointment.appointmentType}")
                                    Text(placeOrLink)
                                    Text(status)
                                    Spacer(Modifier.height(12.dp))
                                    Text(
                                        "¿Desea cancelar la cita?",
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                },
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

                    is AgendaItem.AnalysisItem -> {
                        uiState.selectedAnalysis?.let { analysis ->
                            val status =
                                when (analysis.analysisStatus) {
                                    AnalysisStatus.REQUESTED ->
                                        analysis.analysisStatus?.let { "POR CONFIRMAR" }.orEmpty()

                                    AnalysisStatus.PROGRAMMED ->
                                        analysis.analysisStatus?.let { "CONFIRMADA" }.orEmpty()

                                    else -> ""
                                }
                            val prettyDate = formatDatePretty(analysis.analysisDate)
                            Dialog(
                                title = "Análisis: ${analysis.analysisName}",
                                text = {
                                    Text(
                                        analysis.analysisName,
                                        style = MaterialTheme.typography.titleMedium,
                                    )
                                    Text("Fecha y hora: $prettyDate")
                                    Text("Lugar: ${analysis.place}")
                                    Text("$status")
                                    Text(
                                        "¿Desea cancelar la cita?",
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                },
                                confirmText = "Sí, cancelar",
                                dismissText = "No",
                                onConfirm = {
                                    viewModel.cancelAnalysis(analysis.patientAnalysisId)
                                    showDialog = false
                                },
                                onDismiss = { showDialog = false },
                            )
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}
