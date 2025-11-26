package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.AgendaItem
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.model.AppointmentsResult
import com.example.nefrovida.domain.model.PatientAnalysis
import com.example.nefrovida.ui.organisms.SimpleCard

@Suppress("ktlint:standard:function-naming")
@Composable
fun AgendaUnifiedList(
    items: List<AgendaItem>,
    onAppointmentClick: (Appointment) -> Unit,
    onAnalysisClick: (PatientAnalysis) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items) { item ->
            when (item) {
                is AgendaItem.AppointmentItem -> {
                    SimpleCard(onClick = { onAppointmentClick(item.appointment) }) {
                        Text(item.appointment.name, style = MaterialTheme.typography.titleMedium)
                        Text("Cita: ${item.appointment.appointmentName}")
                        Text("Fecha: ${item.appointment.date}")
                    }
                }

                is AgendaItem.AnalysisItem -> {
                    SimpleCard(onClick = { onAnalysisClick(item.analysis) }) {
                        Text(item.analysis.analysisName, style = MaterialTheme.typography.titleMedium)
                        Text("Lugar: ${item.analysis.place}")
                        Text("Fecha: ${item.analysis.analysisDate}")
                    }
                }
            }
        }
    }
}
