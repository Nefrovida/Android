package com.example.nefrovida.presentation.screens.catalog.comps

import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.remote.dto.ServiceItemDto
import com.example.nefrovida.presentation.screens.catalog.CatalogViewModel
import com.example.nefrovida.presentation.utils.checkValidDate
import com.example.nefrovida.ui.molecules.DatePickerDialog
import com.example.nefrovida.ui.molecules.ReusableStringDropdown
import com.example.nefrovida.ui.theme.NavyBlue
import com.example.nefrovida.ui.theme.TextGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisForm(
    analysis: ServiceItemDto,
    viewModel: CatalogViewModel,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSubmit: (Int, String) -> Unit,
) {
    val places = listOf("Laboratorio Principal", "Laboratorio Sucursal")

    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf<String?>(null) }
    var place by remember { mutableStateOf<String?>(null) }
    val analysisId = analysis.id
    val analysisName = analysis.name

    var timeExpanded by remember { mutableStateOf(false) }
//    var placeExpanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var availability by remember { mutableStateOf<List<String>>(emptyList()) }

    // Validation: all fields must be filled
    val readyToSubmit = date.isNotBlank() && time != null

    // Combine date and time into ISO format: "2025-12-05T08:00:00"
    fun createDateTimeString(): String =
        if (date.isNotBlank() && time != null) {
            "${date}T$time:00"
        } else {
            ""
        }

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
                text = "Análisis: ${analysis.name}",
                style = MaterialTheme.typography.titleMedium,
            )

            if (!analysis.description.isNullOrBlank()) {
                Text(
                    text = analysis.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }

            Text(
                text = "Costo General: $${analysis.generalCost}",
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "Costo Comunidad: $${analysis.communityCost}",
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

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
                modifier = Modifier.weight(1f),
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
                        if (time != null) {
                            val dateTimeString = createDateTimeString()
                            onSubmit(analysisId, dateTimeString)
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
            onDateSelected = { pickedDate ->
                Log.d("AnalysisForm", "Date selected: $pickedDate")
                if (checkValidDate(pickedDate)) {
                    Log.d("AnalysisForm", "Date is valid, fetching availability")
                    date = pickedDate
                    time = null // Reset time when date changes
                    showDatePicker = false

                    // Fetch availability for the selected date
                    scope.launch {
                        availability =
                            try {
                                viewModel.getAnalysisDateAvailability(analysisName, pickedDate)
                            } catch (e: Exception) {
                                Log.e("AnalysisForm", "Error fetching availability: ${e.message}")
                                emptyList()
                            }
                    }
                } else {
                    Log.w("AnalysisForm", "Selected date is invalid")
                    // TODO: Show snackbar for invalid date
                }
            },
        )
    }
}
