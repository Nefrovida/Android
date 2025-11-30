package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.domain.model.AgendaItem
import com.example.nefrovida.domain.model.AnalysisStatus
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
                    val backgroundColor =
                        if (item.appointment.status == AppointmentStatus.PROGRAMMED) {
                            Color(0xFFDFFFD6)
                        } else if (item.appointment.status == AppointmentStatus.REQUESTED) {
                            Color(0xFFFFFFCC)
                        } else {
                            Color.White
                        }

                    SimpleCard(
                        onClick = { onAppointmentClick(item.appointment) },
                        backgroundColor = backgroundColor,
                    ) {
                        Text(item.appointment.name, style = MaterialTheme.typography.titleMedium)
                        Text("Cita: ${item.appointment.appointmentName}")
                        Text("Fecha: ${item.appointment.date}")
                    }
                }

                is AgendaItem.AnalysisItem -> {
                    val backgroundColor =
                        if (item.analysis.analysisStatus == AnalysisStatus.PROGRAMMED) {
                            Color(0xFFDFFFD6)
                        } else if (item.analysis.analysisStatus == AnalysisStatus.REQUESTED) {
                            Color(0xFFFFFFCC)
                        } else {
                            Color.White
                        }
                    SimpleCard(
                        onClick = { onAnalysisClick(item.analysis) },
                        backgroundColor = backgroundColor,
                    ) {
                        Text(item.analysis.analysisName, style = MaterialTheme.typography.titleMedium)
                        Text("Lugar: ${item.analysis.place}")
                        Text("Fecha: ${item.analysis.analysisDate}")
                    }
                }
            }
        }
    }
}
