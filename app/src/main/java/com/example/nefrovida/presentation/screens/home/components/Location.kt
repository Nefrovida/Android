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
            text = "Contáctanos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = NavyBlue,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        // Address
        ContactInfoRow(
            icon = Icons.Default.LocationOn,
            label = "Dirección",
            value = "Sierra Vertientes #167 Lomas de San Juan\nSan Juan del Río, Querétaro, Méx.",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Business hours
        ContactInfoRow(
            icon = Icons.Default.Schedule,
            label = "Horario",
            value = "Beneficiarios: Lunes - Viernes 7:30am - 3:30pm\nLaboratorio: Lunes - Viernes 7:30am - 9:00am",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Phone
        ContactInfoRow(
            icon = Icons.Default.Phone,
            label = "Teléfono",
            value = "427 101 34 35",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // WhatsApp
        ContactInfoRow(
            icon = Icons.Default.Phone,
            label = "WhatsApp",
            value = "427 219 1068",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Email
        ContactInfoRow(
            icon = Icons.Default.Email,
            label = "Correo",
            value = "nefrovida.a.c@hotmail.com",
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Facebook
        ContactInfoRow(
            icon = Icons.Default.ThumbUp,
            label = "Facebook",
            value = "https://www.facebook.com/NefroVida.ac",
        )

        // Extra spacing at bottom for social media box
        Spacer(modifier = Modifier.height(20.dp))
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
