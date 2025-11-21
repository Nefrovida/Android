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
import com.example.nefrovida.presentation.screens.agenda.PatientAgendaViewModel
import com.example.nefrovida.presentation.screens.home.components.PatientAppointmentList

@Composable
fun LaboratoryScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PatientAgendaViewModel = hiltViewModel(),
) {
    val state by viewModel.listState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
                Text(
                    text = "Error: ${state.error}",
                    modifier = Modifier.align(Alignment.Center),
                )
            }
            else -> {
                PatientAppointmentList(
                    appointments = state.appointments,
                    onCardClick = { appointment ->
                        navController.navigate("appointment_detail/${appointment.id}")
                    },
                )
            }
        }
    }
}
