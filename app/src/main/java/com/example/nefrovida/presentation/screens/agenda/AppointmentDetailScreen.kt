
package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.ui.organisms.AppointmentCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDetailScreen(
    appointmentId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appointment = getMockAppointmentDetails(appointmentId)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppointmentCard(
                name = appointment.name,
                date = appointment.date,
                type = appointment.type,
                duration = appointment.duration,
                onClick = { }
            )

            Text(
                text = "Requerimientos Previos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp) // Un espacio extra arriba
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    appointment.requirements.forEach { requirement ->
                        RequirementItem(text = requirement)
                    }
                }
            }
        }
    }
}

@Composable
private fun RequirementItem(
    text: String,
    modifier: Modifier = Modifier
) {
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

private data class MockAppointment(
    val name: String,
    val date: String,
    val type: String,
    val duration: Int,
    val requirements: List<String>
)

private fun getMockAppointmentDetails(id: String): MockAppointment {
    return MockAppointment(
        name = "Oliver Queen", //
        date = "2025-11-10", //
        type = "PRESENCIAL", //
        duration = 30, //
        requirements = listOf(
            "Presentarse con ayuno de 8 horas.",
            "Traer resultados de laboratorio previos.",
            "Beber 1 litro de agua 30 minutos antes.",
            "Confirmar asistencia 24 horas antes."
        )
    )
}