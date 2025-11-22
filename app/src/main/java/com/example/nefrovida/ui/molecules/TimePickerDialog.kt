package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    availability: List<String>,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .heightIn(min = 100.dp, max = 350.dp) // 👈 keeps it short
                        .verticalScroll(rememberScrollState()), // 👈 scrolls
            ) {
                Text(
                    text = "Horarios Disponibles",
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(Modifier.height(12.dp))

                // --- TIME DROPDOWN ---
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    OutlinedTextField(
                        value = selectedTime,
                        readOnly = true,
                        onValueChange = {},
                        label = { Text("Horario") },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        availability.forEach { time ->
                            DropdownMenuItem(
                                text = { Text(time) },
                                onClick = {
                                    selectedTime = time
                                    expanded = false
                                },
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                // --- ACTIONS ---
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }

                    TextButton(onClick = {
                        if (selectedTime.isNotEmpty()) {
                            onTimeSelected(selectedTime)
                        }
                        onDismiss()
                    }) {
                        Text("Aceptar")
                    }
                }
            }
        }
    }
}
