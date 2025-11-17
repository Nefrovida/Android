package com.example.nefrovida.presentation.screens.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // <-- Importante
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.network.dto.AppointmentDto // <-- Importa el modelo
import com.example.nefrovida.ui.organisms.AppointmentCard
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun AgendaList(
    appointments: List<AppointmentDto>, // <-- Recibe la lista real
    onCardClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        // Ya no usamos 'item { ... }', sino 'items' para iterar sobre la lista
        items(appointments) { appointment ->
            val doctor = appointment.doctor
            
            AppointmentCard(
                name = "${doctor.firstName} ${doctor.lastName}",
                specialty = doctor.specialty,
                // Formateamos la fecha para mostrar la hora
                time = appointment.date.toFormattedTime(), 
                onClick = { onCardClick(appointment.id.toString()) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// Función simple para formatear la hora
private fun String.toFormattedTime(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        "Hora no disponible" // Fallback
    }
}
