package com.example.nefrovida.ui.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppointmentCard(
    appointment: Appointment,
    name: String,
    specialty: String,
    time: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
fun SimpleCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val clickableModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else modifier

    Card(
        modifier = modifier.fillMaxWidth(), // <-- It's not clickable here anymore
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp) // Adjust the padding
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text (
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
                
            // 1. Left Section: Profile Icon
            Icon(
                imageVector = Icons.Default.AccountCircle, // Default icon
                contentDescription = "Perfil de Doctor",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary // Theme color
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 2. Middle Section: Information
            // Use 'weight(1f)' to use all the available space
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Dr. $name", // Add "Dr."
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = specialty, // New info
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = time, // New info
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // 3. Right Section: Navigation Button
            IconButton(onClick = onClick) { // <-- The onClick now resides here
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Ver detalles"
                )
            }
        }
    }
}
