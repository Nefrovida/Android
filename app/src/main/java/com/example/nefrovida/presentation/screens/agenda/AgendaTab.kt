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
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.ui.organisms.SimpleCard

@Suppress("ktlint:standard:function-naming")
@Composable
fun AgendaList(
    appointmentList: List<Appointment>,
    onCardClick: (Appointment) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            items = appointmentList,
            key = { it.id },
        ) { appointment ->

            SimpleCard(
                onClick = { onCardClick(appointment) },
            ) {
                Text(
                    text = appointment.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = "Cita: ${appointment.appointmentName}",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = "Fecha: ${appointment.date}",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}
