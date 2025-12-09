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
            text = "Nuestros Tamizajes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = NavyBlue,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        Text(
            text = "OBJETIVO: Realizar un tamizaje de función renal es detectar de manera temprana alteraciones en la función renal, incluso antes de que aparezcan síntomas clínicos evidentes, para poder prevenir o retrasar la progresión de enfermedades renales crónicas y reducir complicaciones asociadas.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Text(
            text = "INCLUYE: aplicación de cuestionario de factor de riesgo, toma de presión arterial, peso, talla, circunferencia de cintura, estudios de laboratorio de sangre y orina, entrega de estudios impresos, interpretación, recomendaciones y derivación con especialidades.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 24.dp),
        )

        // Service 1: Tamizacion Niños
        ServiceCard(
            icon = Icons.Default.CheckCircle,
            title = "Niños",
            description = "Química Sanguínea de 6 elementos + examen general de orina",
            bulletPoints =
                listOf(
                    "Llama, pregunta por nuestras cuotas de recuperación y agenda",
                ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Service 2: Tamizacion Adultos
        ServiceCard(
            icon = Icons.Default.CheckCircle,
            title = "Adultos",
            description = "Química Sanguínea de 6 elementos + microalbuminuria",
            bulletPoints =
                listOf(
                    "Llama, pregunta por nuestras cuotas de recuperación y agenda",
                ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Service 3: Tamizacion Embarazadas
        ServiceCard(
            icon = Icons.Default.CheckCircle,
            title = "Embarazadas",
            description = "Química Sanguínea de 6 elementos + PFH + BH + examen general de orina",
            bulletPoints =
                listOf(
                    "Llama, pregunta por nuestras cuotas de recuperación y agenda",
                ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Consultas
        Text(
            text = "Consultas",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = NavyBlue,
            modifier = Modifier.padding(bottom = 24.dp),
        )

        ServiceCard(
            icon = Icons.Default.MedicalServices,
            title = "Atención médica especializada",
            description = "Llama, pregunta por nuestras cuotas de recuperación y agenda",
            bulletPoints =
                listOf(
                    "Nefrología",
                    "Nefro pediatra",
                    "Urología",
                    "Diabetólogo",
                    "Médico General",
                    "Nutrición",
                    "Psicología",
                ),
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Ultrasonidos
        Text(
            text = "Ultrasonidos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = NavyBlue,
            modifier = Modifier.padding(bottom = 24.dp),
        )

        ServiceCard(
            icon = Icons.Default.Monitor,
            title = "Realizadas por un médico certificado",
            description = "Con previa cita. Llama, pregunta por nuestras cuotas de recuperación y agenda",
            bulletPoints =
                listOf(
                    "Renal",
                    "Abdomen",
                    "Entre otros más",
                ),
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
                modifier = Modifier.padding(bottom = 12.dp),
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
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                bulletPoints.forEach { point ->
                    Row(
                        verticalAlignment = Alignment.Top,
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
