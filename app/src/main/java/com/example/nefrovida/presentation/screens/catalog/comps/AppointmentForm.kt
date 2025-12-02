package com.example.nefrovida.presentation.screens.catalog.comps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.remote.dto.ServiceItemDto
import com.example.nefrovida.ui.molecules.DatePickerDialog
import com.example.nefrovida.ui.molecules.ReusableIntDropdown
import com.example.nefrovida.ui.molecules.ReusableStringDropdown
import com.example.nefrovida.ui.theme.NavyBlue
import com.example.nefrovida.ui.theme.TextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentForm(
    appointment: ServiceItemDto,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSubmit: (String, String, String, Int, Int) -> Unit,
) {
    val appointmentTypes = listOf("Presencial", "En Línea")
    val places = listOf("Consultorio 01", "Consultorio 02", "Sala Virtual")
    val durations = listOf(30, 45, 60, 90)

    var appointmentType by remember { mutableStateOf<String?>(null) }
    var place by remember { mutableStateOf<String?>(null) }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf<String?>(null) }
    var duration by remember { mutableStateOf<Int?>(30) }
    val doctorId = appointment.id

    var typeExpanded by remember { mutableStateOf(false) }
    var placeExpanded by remember { mutableStateOf(false) }
    var timeExpanded by remember { mutableStateOf(false) }
    var durationExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Validation: all fields must be filled
    val readyToSubmit =
        appointmentType != null && place != null &&
            date.isNotBlank() && time != null && duration != null && duration!! >= 30

    // Combine date and time into ISO format: "2025-12-05T10:00:00"
    fun createDateTimeString(): String =
        if (date.isNotBlank() && time != null) {
            "${date}T$time:00"
        } else {
            ""
        }

    // Available time slots
    val timeSlots =
        listOf(
            "08:00",
            "08:30",
            "09:00",
            "09:30",
            "10:00",
            "10:30",
            "11:00",
            "11:30",
            "12:00",
            "12:30",
            "13:00",
            "13:30",
            "14:00",
            "14:30",
            "15:00",
            "15:30",
            "16:00",
            "16:30",
            "17:00",
            "17:30",
            "18:00",
        )

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp,
            ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Reserva para: ${appointment.name}",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "Doctor: ${appointment.doctor ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Row for type and duration
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ReusableStringDropdown(
                    label = "Tipo",
                    selectedValue = appointmentType,
                    options = appointmentTypes,
                    expanded = typeExpanded,
                    onExpandedChange = { typeExpanded = it },
                    onValueSelected = { appointmentType = it },
                    modifier = Modifier.weight(1f),
                )

                ReusableIntDropdown(
                    label = "Duración",
                    selectedValue = duration,
                    options = durations,
                    expanded = durationExpanded,
                    onExpandedChange = { durationExpanded = it },
                    onValueSelected = { duration = it },
                    displayText = { "$it min" },
                    modifier = Modifier.weight(1f),
                )
            }

            // Row for date, time, and place
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Date picker
                OutlinedTextField(
                    value = date.ifBlank { "Fecha" },
                    readOnly = true,
                    onValueChange = {},
                    label = { Text("Fecha") },
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NavyBlue,
                            unfocusedBorderColor = TextGray,
                            focusedLabelColor = NavyBlue,
                        ),
                )

                // Time dropdown
                ReusableStringDropdown(
                    label = "Hora",
                    selectedValue = time,
                    options = timeSlots,
                    expanded = timeExpanded,
                    onExpandedChange = { timeExpanded = it },
                    onValueSelected = { time = it },
                    modifier = Modifier.weight(1f),
                )

                // Place dropdown
                ReusableStringDropdown(
                    label = "Lugar",
                    selectedValue = place,
                    options = places,
                    expanded = placeExpanded,
                    onExpandedChange = { placeExpanded = it },
                    onValueSelected = { place = it },
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        if (appointmentType != null && place != null && time != null && duration != null) {
                            val dateTimeString = createDateTimeString()
                            onSubmit(appointmentType!!, place!!, dateTimeString, duration!!, doctorId)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = readyToSubmit,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = Color.White,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledContentColor = Color.White.copy(alpha = 0.4f),
                        ),
                ) {
                    Text("Agendar")
                }
            }
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismiss = { showDatePicker = false },
            onDateSelected = { selectedDate ->
                date = selectedDate
            },
        )
    }
}
