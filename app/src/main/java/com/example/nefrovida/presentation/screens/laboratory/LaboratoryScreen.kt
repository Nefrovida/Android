package com.example.nefrovida.presentation.screens.laboratory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
// Importa el ViewModel RENOMBRADO
import com.example.nefrovida.presentation.screens.agenda.PatientAgendaViewModel
// Importa la LISTA NUEVA
import com.example.nefrovida.presentation.screens.home.components.PatientAppointmentList

@Composable
fun LaboratoryScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    // Llama al ViewModel RENOMBRADO
    viewModel: PatientAgendaViewModel = hiltViewModel()
) {
    // Observa el listState (del ViewModel del paciente)
    val state by viewModel.listState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                // Llama a la LISTA NUEVA del paciente
                PatientAppointmentList(
                    appointments = state.appointments,
                    onCardClick = { appointment ->
                        navController.navigate("appointment_detail/${appointment.id}")
                    }
                )
            }
        }
    }
}