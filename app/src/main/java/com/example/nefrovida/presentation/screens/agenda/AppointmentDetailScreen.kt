package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nefrovida.ui.organisms.AppointmentCard
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDetailScreen(
    appointmentId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    // Use the new ViewModel
    viewModel: PatientAgendaViewModel = hiltViewModel()
) {
    // Call the API
    LaunchedEffect(key1 = appointmentId) {
        viewModel.loadAppointmentDetails(appointmentId)
    }

    val state by viewModel.detailState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la Cita") },
                // The rest of the TopAppBar remains the same
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    Text(
                        text = "Error al cargar detalles: ${state.error}",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.appointment != null -> {
                    val appointment = state.appointment!!
                    val doctor = appointment.doctor

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AppointmentCard(
                            name = "${doctor.firstName} ${doctor.lastName}",
                            specialty = doctor.specialty,
                            time = appointment.date.toFormattedTime(),
                            onClick = { }
                        )

                        Text(
                            text = "Requerimientos Previos",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Card(
                            // ... (el resto de la Card se queda igual) ...
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val requirementsList = appointment.requirements
                                    ?.split("\n") ?: listOf("No hay requerimientos.")

                                requirementsList.forEach { requirement ->
                                    RequirementItem(text = requirement)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// RequirementItem and toFormattedTime remain the same as in the previous step
@Composable
private fun RequirementItem(text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(bottom = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

private fun String.toFormattedTime(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        "Hora no disponible"
    }
}
