package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
// import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit,
) {
    var hour by remember { mutableStateOf("10") }
    var minute by remember { mutableStateOf("00") }

    var expandedHour by remember { mutableStateOf(false) }
    var expandedMinute by remember { mutableStateOf(false) }

    val hours = (0..23).map { it.toString().padStart(2, '0') }
    val minutes = (0..59).map { it.toString().padStart(2, '0') }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp,
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Selecciona la hora",
                    style = MaterialTheme.typography.titleLarge,
                )

                Spacer(Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // --- HOUR PICKER ---
                    ExposedDropdownMenuBox(
                        expanded = expandedHour,
                        onExpandedChange = { expandedHour = !expandedHour },
                    ) {
                        OutlinedTextField(
                            value = hour,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Hora") },
                            modifier = Modifier.menuAnchor().weight(1f),
                        )

                        ExposedDropdownMenu(
                            expanded = expandedHour,
                            onDismissRequest = { expandedHour = false },
                        ) {
                            hours.forEach { h ->
                                DropdownMenuItem(
                                    text = { Text(h) },
                                    onClick = {
                                        hour = h
                                        expandedHour = false
                                    },
                                )
                            }
                        }
                    }

                    // --- MINUTE PICKER ---
                    ExposedDropdownMenuBox(
                        expanded = expandedMinute,
                        onExpandedChange = { expandedMinute = !expandedMinute },
                    ) {
                        OutlinedTextField(
                            value = minute,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Minuto") },
                            modifier = Modifier.menuAnchor().weight(1f),
                        )

                        ExposedDropdownMenu(
                            expanded = expandedMinute,
                            onDismissRequest = { expandedMinute = false },
                        ) {
                            minutes.forEach { m ->
                                DropdownMenuItem(
                                    text = { Text(m) },
                                    onClick = {
                                        minute = m
                                        expandedMinute = false
                                    },
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }

                    TextButton(onClick = {
                        onTimeSelected("$hour:$minute")
                        onDismiss()
                    }) {
                        Text("Aceptar")
                    }
                }
            }
        }
    }
}
