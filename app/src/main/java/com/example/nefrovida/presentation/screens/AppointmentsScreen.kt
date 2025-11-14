package com.example.nefrovida.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.domain.model.DayItem
import com.example.nefrovida.ui.theme.*

/**
 * Pantalla principal para consultar citas médicas
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun appointmentsScreen(
    appointments: List<Appointment>,
    selectedDay: DayItem,
    days: List<DayItem>,
    onDaySelected: (DayItem) -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBarNefroVida(
                onProfileClick = onProfileClick,
                onSettingsClick = onSettingsClick,
            )
        },
        containerColor = Color.White,
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Selector de fecha
            DaySelector(
                days = days,
                selectedDay = selectedDay,
                onDaySelected = onDaySelected,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de agenda del día
            ScheduleList(
                appointments = appointments,
            )
        }
    }
}

/**
 * Barra superior con logo NEFROVida e íconos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarNefroVida(
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text =
                        buildAnnotatedString {
                            withStyle(
                                style =
                                    SpanStyle(
                                        color = NefroBlue,
                                        fontWeight = FontWeight.Bold,
                                    ),
                            ) {
                                append("NEFRO")
                            }
                            withStyle(
                                style =
                                    SpanStyle(
                                        color = NefroGreen,
                                        fontWeight = FontWeight.Bold,
                                    ),
                            ) {
                                append("Vida")
                            }
                        },
                    fontSize = 24.sp,
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = NefroBlue,
                )
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configuración",
                    tint = NefroBlue,
                )
            }
        },
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                titleContentColor = NefroBlue,
            ),
    )
}

/**
 * Selector de días de la semana con scroll horizontal
 */
@Composable
fun DaySelector(
    days: List<DayItem>,
    selectedDay: DayItem,
    onDaySelected: (DayItem) -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
    ) {
        // Encabezado del mes
        Text(
            text = "Octubre",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = NefroBlue,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
            textAlign = TextAlign.Center,
        )

        // Fila horizontal de días
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            days.forEach { day ->
                DayCard(
                    day = day,
                    isSelected = day.dayNumber == selectedDay.dayNumber,
                    onClick = { onDaySelected(day) },
                )
            }
        }
    }
}

/**
 * Tarjeta individual para un día
 */
@Composable
fun DayCard(
    day: DayItem,
    isSelected: Boolean,
    onClick: () -> Unit = {},
) {
    Card(
        modifier =
            Modifier
                .width(60.dp)
                .height(70.dp)
                .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = if (isSelected) NefroSelectedBlue else Color.White,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = day.dayOfWeek,
                fontSize = 12.sp,
                color = if (isSelected) Color.White else NefroTextGray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = day.dayNumber.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else NefroBlue,
            )
        }
    }
}

/**
 * Lista vertical de horas con citas
 */
@Composable
fun ScheduleList(appointments: List<Appointment>) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Horas del día de 8 AM a 4 PM
        val hours = (8..16).toList()

        items(hours.size) { index ->
            val hour = hours[index]
            val appointment = appointments.find { it.hour == hour }

            ScheduleRow(
                hour = hour,
                appointment = appointment,
            )
        }

        // Espacio al final para mejor visualización
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Fila individual de la agenda con hora y cita
 */
@Composable
fun ScheduleRow(
    hour: Int,
    appointment: Appointment?,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Hora
        Text(
            text = formatHour(hour),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = NefroBlue,
            modifier = Modifier.width(70.dp),
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Cita (si existe)
        if (appointment != null) {
            AppointmentCard(appointment = appointment)
        } else {
            // Espacio vacío si no hay cita
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

/**
 * Tarjeta de cita con nombre del paciente
 */
@Composable
fun AppointmentCard(appointment: Appointment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = NefroLightGray,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 1.dp,
            ),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = appointment.patientName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
            )
        }
    }
}

/**
 * Formatea la hora de 24h a formato AM/PM
 */
fun formatHour(hour: Int): String =
    when {
        hour == 0 -> "12 AM"
        hour < 12 -> "$hour AM"
        hour == 12 -> "12 PM"
        else -> "${hour - 12} PM"
    }

/**
 * Vista previa de la pantalla
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppointmentsScreenPreview() {
    NefrovidaTheme {
        val sampleAppointments =
            listOf(
                Appointment(
                    id = 1,
                    patientName = "Ramón Macías García",
                    hour = 8,
                    date = "2024-10-16",
                ),
                Appointment(
                    id = 2,
                    patientName = "Fabiola Rosales López",
                    hour = 9,
                    date = "2024-10-16",
                ),
            )

        val sampleDays =
            listOf(
                DayItem("lun", 13, false),
                DayItem("mar", 14, false),
                DayItem("mié", 15, false),
                DayItem("jue", 16, true),
                DayItem("vie", 17, false),
                DayItem("sáb", 18, false),
                DayItem("dom", 19, false),
            )

        val selectedDay = sampleDays.find { it.isSelected } ?: sampleDays[3]

        AppointmentsScreen(
            appointments = sampleAppointments,
            selectedDay = selectedDay,
            days = sampleDays,
        )
    }
