package com.example.nefrovida.presentation.screens.agenda

// --- IMPORTACIONES AÑADIDAS ---
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
// --- FIN DE IMPORTACIONES ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDetailScreen(
    appointmentId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PatientAgendaViewModel = hiltViewModel() // Ahora sí lo encuentra
) {
    LaunchedEffect(key1 = appointmentId) {
        viewModel.loadAppointmentDetails(appointmentId) // Ahora sí lo encuentra
    }

    val state by viewModel.detailState.collectAsState() // Ahora sí lo encuentra

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la Cita") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
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
                state.isLoading -> { // Ahora sí lo encuentra
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> { // Ahora sí lo encuentra
                    Text(
                        text = "Error al cargar detalles: ${state.error}", // Ahora sí lo encuentra
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.appointment != null -> { // Ahora sí lo encuentra
                    val appointment = state.appointment!! // Ahora sí lo encuentra
                    val doctor = appointment.doctor // Ahora sí lo encuentra

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AppointmentCard(
                            name = "${doctor.firstName} ${doctor.lastName}",
                            specialty = doctor.specialty,
                            time = appointment.date.toFormattedTime(), // Ahora sí lo encuentra
                            onClick = { }
                        )

                        Text(
                            text = "Requerimientos Previos",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val requirementsList = appointment.requirements // Ahora sí lo encuentra
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