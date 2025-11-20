package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.ui.molecules.DatePickerDialog
import com.example.nefrovida.ui.molecules.TimePickerDialog
import com.example.nefrovida.ui.organisms.SimpleCard

@Composable
fun RescheduleFormCard(
    modifier: Modifier = Modifier,
    appointment: Appointment?,
    onReschedule: (reason: String, date: String, time: String) -> Unit,
    onCancel: () -> Unit,
) {
    var reason by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val formReady = reason.isNotBlank() && date.isNotBlank() && time.isNotBlank()

    SimpleCard(modifier.padding(8.dp)) {
        Text(
            text = "Reprogramar cita",
            style = MaterialTheme.typography.titleLarge,
        )

        Text("Paciente: ${appointment?.name}")
        Text("Cita: ${appointment?.appointmentName}")
        Text("Fecha actual: ${appointment?.date}")
        Text("Estatus: ${appointment?.status}")

        // --- Reason Input ---
        OutlinedTextField(
            value = reason,
            onValueChange = { reason = it },
            label = { Text("Motivo de reprogramación") },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            minLines = 3,
        )

        // --- Date & Time row ---
        Row(
            modifier = Modifier.padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedTextField(
                value = date,
                onValueChange = {},
                label = { Text("Nueva fecha") },
                modifier =
                    Modifier
                        .weight(1f)
                        .clickable { showDatePicker = true },
                enabled = false,
            )

            OutlinedTextField(
                value = time,
                onValueChange = {},
                label = { Text("Nueva hora") },
                modifier =
                    Modifier
                        .weight(1f)
                        .clickable { showTimePicker = true },
                enabled = false,
            )
        }

        // --- Date Picker ---
        if (showDatePicker) {
            DatePickerDialog(
                onDismiss = { showDatePicker = false },
                onDateSelected = {
                    date = it
                    showDatePicker = false
                },
            )
        }

        // --- Time Picker ---
        if (showTimePicker) {
            TimePickerDialog(
                onDismiss = { showTimePicker = false },
                onTimeSelected = {
                    time = it
                    showTimePicker = false
                },
            )
        }

        Spacer(Modifier.height(16.dp))

        // --- Action Buttons ---
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f),
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = { onReschedule(reason, date, time) },
                modifier = Modifier.weight(1f),
                enabled = formReady,
            ) {
                Text("Reprogramar")
            }
        }
    }
}
