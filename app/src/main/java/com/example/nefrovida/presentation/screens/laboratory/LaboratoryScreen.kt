package com.example.nefrovida.presentation.screens.laboratory

// --- IMPORTACIONES AÑADIDAS ---
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
import com.example.nefrovida.presentation.screens.agenda.PatientAgendaViewModel // <-- La clave
import com.example.nefrovida.presentation.screens.home.components.PatientAppointmentList // <-- La clave
// --- FIN DE IMPORTACIONES ---

@Composable
fun LaboratoryScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PatientAgendaViewModel = hiltViewModel() // Ahora sí lo encuentra
) {
    val state by viewModel.listState.collectAsState() // Ahora sí lo encuentra

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> { // Ahora sí lo encuentra
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> { // Ahora sí lo encuentra
                Text(
                    text = "Error: ${state.error}", // Ahora sí lo encuentra
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                PatientAppointmentList( // Ahora sí lo encuentra
                    appointments = state.appointments, // Ahora sí lo encuentra
                    onCardClick = { appointment ->
                        navController.navigate("appointment_detail/${appointment.id}") // Ahora sí lo encuentra
                    }
                )
            }
        }
    }
}