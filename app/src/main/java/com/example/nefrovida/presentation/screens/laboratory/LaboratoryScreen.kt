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
import com.example.nefrovida.presentation.screens.agenda.AgendaViewModel // Importa el ViewModel
import com.example.nefrovida.presentation.screens.home.components.AgendaList

@Composable
fun LaboratoryScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    // Hilt inyectará automáticamente el ViewModel
    viewModel: AgendaViewModel = hiltViewModel()
) {
    // Observamos el estado (state) del ViewModel
    val state by viewModel.listState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Usamos un 'when' para reaccionar al estado
        when {
            // --- ESTADO DE CARGA ---
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            // --- ESTADO DE ERROR ---
            state.error != null -> {
                Text(
                    text = "Error al cargar citas: ${state.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            // --- ESTADO DE ÉXITO ---
            else -> {
                // Si todo está bien, pasamos la lista real a AgendaList
                AgendaList(
                    appointments = state.appointments, // <-- Datos reales
                    onCardClick = { appointmentId ->
                        navController.navigate("appointment_detail/$appointmentId")
                    }
                )
            }
        }
    }
}
