package com.example.nefrovida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.presentation.screens.AppointmentsScreen
import com.example.nefrovida.presentation.viewmodel.AppointmentsUiState
import com.example.nefrovida.presentation.viewmodel.AppointmentsViewModel
import com.example.nefrovida.ui.theme.NefrovidaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AppointmentsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NefrovidaTheme {
                val uiState by viewModel.uiState.collectAsState()
                val appointments by viewModel.appointments.collectAsState()
                val days by viewModel.days.collectAsState()
                val selectedDay by viewModel.selectedDay.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    when (uiState) {
                        is AppointmentsUiState.Loading -> {
                            LoadingScreen()
                        }
                        is AppointmentsUiState.Success, is AppointmentsUiState.Empty -> {
                            selectedDay?.let { day ->
                                AppointmentsScreen(
                                    appointments = appointments,
                                    selectedDay = day,
                                    days = days,
                                    onDaySelected = { viewModel.onDaySelected(it) },
                                    onProfileClick = { /* TODO: Navegar al perfil */ },
                                    onSettingsClick = { /* TODO: Navegar a ajustes */ },
                                )
                            }
                        }
                        is AppointmentsUiState.Error -> {
                            ErrorScreen(
                                message = (uiState as AppointmentsUiState.Error).message,
                                onRetry = { viewModel.refresh() },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Error: $message",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
            )
            Button(onClick = onRetry) {
                Text("Reintentar")
            }
        }
    }
}
