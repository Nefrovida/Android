package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.domain.model.Appointment
import com.example.nefrovida.presentation.utils.checkValidDate
import com.example.nefrovida.ui.molecules.DatePickerDialog
import com.example.nefrovida.ui.molecules.TimePickerDialog
import com.example.nefrovida.ui.organisms.SimpleCard
import kotlinx.coroutines.launch

@Composable
fun RescheduleFormCard(
    modifier: Modifier = Modifier,
    appointment: Appointment?,
    getDateAvailability: suspend (
        appointmentName: String,
        date: String,
    ) -> List<String>,
    onReschedule: (reason: String, date: String, time: String) -> Unit,
    onCancel: () -> Unit,
) {
    var reason by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val formReady = reason.isNotBlank() && date.isNotBlank() && time.isNotBlank()
    var scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()
    var availability by remember { mutableStateOf<List<String>>(emptyList()) }

//    SimpleCard(modifier.padding(8.dp)) {
    SimpleCard(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState()),
    ) {
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
            Box(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = date,
                    onValueChange = {},
                    label = { Text("Nueva fecha") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                )
                // Invisible clickable layer on top
                Box(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .clickable { showDatePicker = true },
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = time,
                    onValueChange = {},
                    label = { Text("Nueva hora") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                )
                // Invisible clickable layer on top
                Box(
                    modifier =
                        Modifier
                            .matchParentSize()
                            .clickable { showTimePicker = true },
                )
            }
        }

        // --- Date Picker ---
        if (showDatePicker) {
            DatePickerDialog(
                onDismiss = { showDatePicker = false },
                onDateSelected = { picked ->
                    if (checkValidDate(picked)) {
                        date = picked
                        showDatePicker = false

                        scope.launch {
                            availability =
                                try {
                                    getDateAvailability(appointment?.appointmentName ?: "", picked)
                                } catch (e: Exception) {
                                    emptyList()
                                }
                        }

                        // TODO: If no times available:
                        // if (availability.value.isEmpty()) showSnackbar("No hay horarios")
                    } else {
                        // TODO: show snackbar for the invalid date error
                        println("Selected date is invalid")
                    }
                },
            )
        }

        // --- Time Picker ---
        if (showTimePicker) {
            TimePickerDialog(
                availability = availability,
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
