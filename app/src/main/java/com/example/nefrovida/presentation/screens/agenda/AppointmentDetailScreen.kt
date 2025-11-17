// Guarda esto en:
// presentation/screens/agenda/AppointmentDetailScreen.kt

package com.example.nefrovida.presentation.screens.agenda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.ui.organisms.AppointmentCard

/**
 * Muestra los detalles de una cita específica, incluyendo los
 * requerimientos previos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentDetailScreen(
    appointmentId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // --- Simulación de datos ---
    val appointment = getMockAppointmentDetails(appointmentId)
    // --- Fin de simulación ---

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de la Cita") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp), // Padding general del contenido
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- 👇 AQUÍ ESTÁ LA CORRECCIÓN 👇 ---
            // Usamos los nuevos parámetros: specialty y time
            AppointmentCard(
                name = appointment.name,
                specialty = appointment.specialty, // <-- NUEVO
                time = appointment.time,           // <-- NUEVO
                onClick = { } // No se necesita acción de click aquí
            )
            // --- FIN DE LA CORRECCIÓN ---

            // 2. Esta es la sección de "Requerimientos"
            Text(
                text = "Requerimientos Previos",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp) // Un espacio extra arriba
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    appointment.requirements.forEach { requirement ->
                        RequirementItem(text = requirement)
                    }
                }
            }
        }
    }
}

/**
 * Un componente interno simple para mostrar un ítem de requerimiento.
 */
@Composable
private fun RequirementItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(bottom = 8.dp) // Espacio entre ítems
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary // Usa el color primario del tema
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// --- Simulación de datos (Mock Data) ---
// Actualizamos la data de muestra para que coincida
// con la nueva estructura.

private data class MockAppointment(
    val name: String,
    val specialty: String, // <-- NUEVO
    val time: String,      // <-- NUEVO
    val requirements: List<String>
)

private fun getMockAppointmentDetails(id: String): MockAppointment {
    // Dependiendo del ID que recibimos de AgendaTab.kt,
    // mostramos un doctor u otro.
    return if (id == "1") {
        MockAppointment(
            name = "Oliver Queen",
            specialty = "Nefrología",
            time = "10:30 AM",
            requirements = listOf(
                "Presentarse con ayuno de 8 horas.",
                "Traer resultados de laboratorio previos.",
                "Beber 1 litro de agua 30 minutos antes.",
                "Confirmar asistencia 24 horas antes."
            )
        )
    } else {
        MockAppointment(
            name = "Barry Allen",
            specialty = "Cardiología",
            time = "11:00 AM",
            requirements = listOf(
                "Traer electrocardiograma reciente.",
                "No tomar café 6 horas antes.",
                "Presentarse con ropa cómoda."
            )
        )
    }
}