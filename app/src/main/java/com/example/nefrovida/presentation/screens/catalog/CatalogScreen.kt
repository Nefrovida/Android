package com.example.nefrovida.presentation.screens.catalog

import AnalysisList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.data.remote.dto.ServiceItemDto
import com.example.nefrovida.presentation.screens.catalog.comps.AnalysisForm
import com.example.nefrovida.presentation.screens.catalog.comps.AppointmentForm
import com.example.nefrovida.presentation.screens.catalog.comps.AppointmentList
import com.example.nefrovida.ui.molecules.ResultsToggleButton
import kotlinx.coroutines.launch

@Suppress("ktlint:standard:function-naming")
@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsState()
    var currentView by remember { mutableStateOf(CatalogViewType.APPOINTMENTS) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf<ServiceItemDto?>(null) }

    if (state.showCreateSuccess) {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar("Reserva creada con éxito")
                viewModel.resetCreateSuccess()
            }
        }
    }

    if (state.showCreateError) {
        LaunchedEffect(Unit) {
            scope.launch {
                snackbarHostState.showSnackbar("Error al crear la reserva")
                viewModel.resetCreateError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Error: ${state.error}")
                    }
                }

                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Only show toggle when not in a form
                        if (currentView != CatalogViewType.APPOINTMENT_FORM && currentView != CatalogViewType.LAB_FORM) {
                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Row(
                                    modifier =
                                        Modifier
                                            .background(Color(0xFFF2F2F7), RoundedCornerShape(50))
                                            .padding(4.dp)
                                            .fillMaxWidth(),
                                ) {
                                    ResultsToggleButton(
                                        text = "Consultas",
                                        selected = currentView == CatalogViewType.APPOINTMENTS,
                                        onClick = { currentView = CatalogViewType.APPOINTMENTS },
                                        modifier = Modifier.weight(1f),
                                    )

                                    ResultsToggleButton(
                                        text = "Laboratorio",
                                        selected = currentView == CatalogViewType.LAB,
                                        onClick = { currentView = CatalogViewType.LAB },
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                            }
                        }
                        when (currentView) {
                            CatalogViewType.APPOINTMENTS -> {
                                AppointmentList(
                                    services = state.appointments,
                                    onReserve = { item ->
                                        selectedItem = item
                                        currentView = CatalogViewType.APPOINTMENT_FORM
                                    },
                                )
                            }

                            CatalogViewType.LAB -> {
                                AnalysisList(
                                    services = state.analysis,
                                    onReserve = { item ->
                                        selectedItem = item
                                        currentView = CatalogViewType.LAB_FORM
                                    },
                                )
                            }

                            CatalogViewType.APPOINTMENT_FORM -> {
                                selectedItem?.let { appointment ->
                                    AppointmentForm(
                                        appointment = appointment,
                                        viewModel = viewModel,
                                        modifier = Modifier,
                                        onDismiss = {
                                            selectedItem = null
                                            currentView = CatalogViewType.APPOINTMENTS
                                        },
                                        onSubmit = { type, dateHour, appointmentId ->
                                            viewModel.createAppointment(
                                                appointmentType = type,
                                                dateHour = dateHour,
                                                appointmentId = appointmentId,
                                            )
                                            selectedItem = null
                                            currentView = CatalogViewType.APPOINTMENTS
                                        },
                                    )
                                }
                            }

                            CatalogViewType.LAB_FORM -> {
                                selectedItem?.let { analysis ->
                                    AnalysisForm(
                                        analysis = analysis,
                                        viewModel = viewModel,
                                        modifier = Modifier,
                                        onDismiss = {
                                            selectedItem = null
                                            currentView = CatalogViewType.LAB
                                        },
                                        onSubmit = { analysisId, analysisDate ->
                                            viewModel.createAnalysisAppointment(
                                                analysisId = analysisId,
                                                analysisDate = analysisDate,
                                            )
                                            selectedItem = null
                                            currentView = CatalogViewType.LAB
                                        },
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

enum class CatalogViewType {
    APPOINTMENTS,
    APPOINTMENT_FORM,
    LAB,
    LAB_FORM,
}
