package com.example.nefrovida.presentation.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.AppointmentNotes

@Composable
fun AppointmentNoteCard(item: AppointmentNotes) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(vertical = 8.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
        ) {
            // --- Siempre visible ---
            Text(
                text = item.appointmentName,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(text = item.date)

            Spacer(modifier = Modifier.height(12.dp))

            // --- Solo visible si expanded == true ---
            if (expanded) {
                item.notes.forEach { note ->
                    Text(text = "Notas", style = MaterialTheme.typography.titleMedium)
                    Text(text = note.generalNotes)

                    Text(text = "Observaciones", style = MaterialTheme.typography.titleMedium)
                    Text(text = note.ailments)

                    Text(text = "Tratamiento", style = MaterialTheme.typography.titleMedium)
                    Text(text = note.prescription)

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        // --- Botón cambia entre Ver más / Ocultar ---
        TextButton(
            onClick = { expanded = !expanded },
            modifier =
                Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 12.dp),
        ) {
            Text(
                text = if (expanded) "Ocultar" else "Ver más",
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}
