package com.example.nefrovida.presentation.screens.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.network.dto.AppointmentDto // <-- Uses the patient's DTO
import com.example.nefrovida.ui.organisms.AppointmentCard // <-- Re-uses the card
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PatientAppointmentList(
    appointments: List<AppointmentDto>, // <-- Receives the DTOs list
    onCardClick: (AppointmentDto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(appointments) { appointment ->
            val doctor = appointment.doctor

            // Calls the AppointmentCard with DTO data
            AppointmentCard(
                name = "${doctor.firstName} ${doctor.lastName}",
                specialty = doctor.specialty,
                time = appointment.date.toFormattedTime(), // Uses the help function
                onClick = { onCardClick(appointment) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// Help function to format the time
private fun String.toFormattedTime(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date = inputFormat.parse(this)
        outputFormat.format(date)
    } catch (e: Exception) {
        "Hora no disponible"
    }
}
