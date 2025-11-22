package com.example.nefrovida.presentation.screens.agenda

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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

    // Nueva fecha seleccionada
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
    ) { paddingValues ->
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            // --- CALENDARIO SEMANAL ---
            WeeklyCalendarView(
                selectedDate = selectedDate,
                onDateSelected = { date ->
                    selectedDate = date
                    val formattedDate = date.toString() // <- convierte LocalDate a String
                    viewModel.loadAgendaList(formattedDate)
                },
                modifier = Modifier.padding(8.dp),
            )

            // --- LISTA DE CITAS ---
            AgendaList(
                appointmentList = appointments ?: emptyList(),
                onCardClick = { appointment ->
                    viewModel.getAppointment(appointment.id)
                    showDialog = true
                },
            )

            // --- DIALOGO PARA CANCELAR ---
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
                            Log.d("AgendaScreen", "appointment id: ${appointment.id}")
                            showDialog = false
                        },
                        onDismiss = { showDialog = false },
                    )
                }
            }
        }
    }
}
