package com.example.nefrovida.presentation.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nefrovida.ui.theme.NavyBlue
import com.example.nefrovida.ui.theme.White

@Composable
fun OurServicesSection() {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
    ) {
        Text(
            text = "Nuestros Servicios",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = NavyBlue,
            modifier = Modifier.padding(bottom = 24.dp),
        )

        // Service 1: Tamizacion y Prevencion
        ServiceCard(
            icon = Icons.Default.CheckCircle,
            title = "Tamización y Prevención",
            description = "Detección temprana de enfermedad renal crónica",
            bulletPoints = listOf(
                "Niños (donativo $180)",
                "Adultos (donativo $200)",
                "Embarazadas (donativo $395)"
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Service 2: Consultas
        ServiceCard(
            icon = Icons.Default.MedicalServices,
            title = "Consultas",
            description = "Atención médica especializada",
            bulletPoints = listOf(
                "Nefrología",
                "Nefro Pediatra",
                "Urología",
                "Diabetólogo",
                "Médico General",
                "Nutrición",
                "Psicología"
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Service 3: Ultrasonidos
        ServiceCard(
            icon = Icons.Default.Monitor,
            title = "Ultrasonidos",
            description = "Realizados por un médico certificado",
            bulletPoints = listOf(
                "Renal",
                "Abdomen",
                "Próstata",
                "Tiroides",
                "Obstétrico",
                "Tejidos blandos",
                "Hernias",
                "Testicular",
                "Mama"
            )
        )
    }
}

@Composable
fun ServiceCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    bulletPoints: List<String>,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = White,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
        ) {
            // Icon and Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(40.dp),
                    tint = NavyBlue,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = NavyBlue,
                )
            }

            // Description
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp),
            )

            // Bullet points
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                bulletPoints.forEach { point ->
                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "• ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = NavyBlue,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = point,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}

