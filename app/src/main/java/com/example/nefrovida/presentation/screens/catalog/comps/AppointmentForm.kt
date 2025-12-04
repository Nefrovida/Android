package com.example.nefrovida.presentation.screens.catalog.comps

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.remote.dto.ServiceItemDto
import com.example.nefrovida.presentation.screens.catalog.CatalogViewModel
import com.example.nefrovida.presentation.utils.checkValidDate
import com.example.nefrovida.ui.molecules.DatePickerDialog
import com.example.nefrovida.ui.molecules.ReusableIntDropdown
import com.example.nefrovida.ui.molecules.ReusableStringDropdown
import com.example.nefrovida.ui.theme.NavyBlue
import com.example.nefrovida.ui.theme.TextGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentForm(
    appointment: ServiceItemDto,
    viewModel: CatalogViewModel,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSubmit: (String, String, Int) -> Unit,
) {
    val appointmentTypes = listOf("Presencial", "Virtual")

    var appointmentType by remember { mutableStateOf<String?>(null) }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf<String?>(null) }
    val appointmentId = appointment.id

    var typeExpanded by remember { mutableStateOf(false) }
    var timeExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var availability by remember { mutableStateOf<List<String>>(emptyList()) }

    // Validation: all fields must be filled
    val readyToSubmit =
        appointmentType != null && date.isNotBlank() && time != null

    // Combine date and time into ISO format: "2025-12-05T10:00:00"
    fun createDateTimeString(): String =
        if (date.isNotBlank() && time != null) {
            "${date}T$time:00"
        } else {
            ""
        }

    // Generate all time slots from 9 AM to 5 PM every 10 minutes
    fun generateAllTimeSlots(): List<String> {
        val slots = mutableListOf<String>()
        for (hour in 9..16) { // 9 AM to 4 PM (16:00 is 4 PM, will generate up to 16:50)
            for (minute in 0..50 step 10) {
                val hourStr = hour.toString().padStart(2, '0')
                val minuteStr = minute.toString().padStart(2, '0')
                slots.add("$hourStr:$minuteStr")
            }
        }
        // Add 5:00 PM (17:00) as the last slot
        slots.add("17:00")
        return slots
    }

    // Filter out occupied times from the API response
    fun getAvailableTimeSlots(occupiedTimes: List<String>): List<String> {
        val allSlots = generateAllTimeSlots()
        return allSlots.filter { it !in occupiedTimes }
    }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
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
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
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

            // Appointment type dropdown
            ReusableStringDropdown(
                label = "Tipo de Consulta",
                selectedValue = appointmentType,
                options = appointmentTypes,
                expanded = typeExpanded,
                onExpandedChange = { typeExpanded = it },
                onValueSelected = { appointmentType = it },
                modifier = Modifier.fillMaxWidth(),
            )

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
                options = availability,
                expanded = timeExpanded,
                onExpandedChange = { timeExpanded = it },
                onValueSelected = { time = it },
                placeholder =
                    if (date.isBlank()) {
                        "Seleccione fecha"
                    } else if (availability.isEmpty()) {
                        "Sin horarios"
                    } else {
                        "Seleccionar"
                    },
                modifier = Modifier.fillMaxWidth(),
            )

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
                        if (appointmentType != null && time != null) {
                            val dateTimeString = createDateTimeString()
                            onSubmit(appointmentType!!, dateTimeString, appointmentId)
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
                    Text("Citar")
                }
            }
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismiss = { showDatePicker = false },
            onDateSelected = { pickedDate ->
                Log.d("AppointmentForm", "Date selected: $pickedDate")
                if (checkValidDate(pickedDate)) {
                    Log.d("AppointmentForm", "Date is valid, fetching availability")
                    date = pickedDate
                    time = null // Reset time when date changes
                    showDatePicker = false

                    // Fetch occupied times for the selected date and appointmentId
                    scope.launch {
                        val occupiedTimes =
                            try {
                                viewModel.getDateAvailability(pickedDate, appointmentId)
                            } catch (e: Exception) {
                                Log.e("AppointmentForm", "Error fetching availability: ${e.message}")
                                emptyList()
                            }
                        // Convert occupied times to available times
                        availability = getAvailableTimeSlots(occupiedTimes)
                        Log.d("AppointmentForm", "Occupied times: $occupiedTimes")
                        Log.d("AppointmentForm", "Available times: $availability")
                    }
                } else {
                    Log.w("AppointmentForm", "Selected date is invalid")
                    // TODO: Show snackbar for invalid date
                }
            },
        )
    }
}
