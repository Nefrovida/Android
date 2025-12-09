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
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.remote.dto.AppointmentStatus
import com.example.nefrovida.domain.model.AgendaItem
import com.example.nefrovida.domain.model.AnalysisStatus
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.model.PatientAnalysis
import com.example.nefrovida.presentation.utils.formatDatePretty
import com.example.nefrovida.presentation.utils.formatDatePretty2
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
                        Text("Doctor: ${item.appointment.name}", style = MaterialTheme.typography.titleMedium)
                        Text("Cita: ${item.appointment.appointmentName}", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            "Fecha y hora: ${formatDatePretty2(item.appointment.date, item.appointment.time + ":00")} ",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        when (item.appointment.status) {
                            AppointmentStatus.PROGRAMMED ->
                                Text("CONFIRMADA", style = MaterialTheme.typography.bodyMedium)

                            AppointmentStatus.REQUESTED ->
                                Text("POR CONFIRMAR", style = MaterialTheme.typography.bodyMedium)

                            else ->
                                Text("SIN ESTATUS", style = MaterialTheme.typography.bodyMedium)
                        }
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
                        Text(item.analysis.analysisName ?: "Sin nombre", style = MaterialTheme.typography.titleMedium)
                        Text("Lugar: ${item.analysis.place}", style = MaterialTheme.typography.bodyMedium)
                        Text(
                            "Fecha y hora: ${formatDatePretty(item.analysis.analysisDate)}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        when (item.analysis.analysisStatus) {
                            AnalysisStatus.PROGRAMMED ->
                                Text("CONFIRMADA", style = MaterialTheme.typography.bodyMedium)

                            AnalysisStatus.REQUESTED ->
                                Text("POR CONFIRMAR", style = MaterialTheme.typography.bodyMedium)

                            else ->
                                Text("SIN ESTATUS", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
