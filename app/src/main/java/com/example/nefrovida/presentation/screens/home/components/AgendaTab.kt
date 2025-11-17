package com.example.nefrovida.presentation.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.Appointment // Importa el Modelo
import com.example.nefrovida.ui.organisms.AppointmentCard
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AgendaList(
    appointments: List<Appointment>,
    onCardClick: (Appointment) -> Unit,
    onCancelClick: (Appointment) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(appointments) { appointment ->
            // --- ¡ESTA ES LA CORRECCIÓN! ---
            // Asumimos que el modelo de dominio también tiene un doctor anidado
            // Si no es así, este modelo 'Appointment' está mal definido
            val name = appointment.doctor?.firstName ?: "Doctor"
            val specialty = appointment.doctor?.specialty ?: "Especialidad"

            AppointmentCard(
                name = name,
                specialty = specialty,
                time = appointment.date.toFormattedTime(),
                onClick = { onCardClick(appointment) }
            )

            Button(
                onClick = { onCancelClick(appointment) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.padding(top = 4.dp)
            ) {
                Text("Cancelar Cita")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// (La función toFormattedTime() se queda igual)
private fun String.toFormattedTime(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) { "Hora no disponible" }
}