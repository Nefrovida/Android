package com.example.nefrovida.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nefrovida.ui.theme.LightGray
import com.example.nefrovida.ui.theme.NavyBlue

@Composable
fun EncuentranosSection() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(LightGray)
                .padding(24.dp),
    ) {
        Text(
            text = "Encuéntranos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = NavyBlue,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        // Address
        ContactInfoRow(
            icon = Icons.Default.LocationOn,
            label = "Dirección",
            value = "Calle Principal #123, Colonia Centro, Ciudad, CP 12345",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Business hours
        ContactInfoRow(
            icon = Icons.Default.Schedule,
            label = "Horario",
            value = "Lunes a Viernes: 8:00 AM - 6:00 PM\nSábados: 9:00 AM - 2:00 PM",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Phone
        ContactInfoRow(
            icon = Icons.Default.Phone,
            label = "Teléfono",
            value = "+52 (123) 456-7890",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Email
        ContactInfoRow(
            icon = Icons.Default.Email,
            label = "Correo",
            value = "contacto@nefrovida.org",
        )

        // Extra spacing at bottom for social media box
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ContactInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(24.dp),
            tint = NavyBlue,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = NavyBlue,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

