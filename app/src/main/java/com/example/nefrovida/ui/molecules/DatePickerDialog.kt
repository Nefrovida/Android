package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit,
    dateValidator: (Long) -> Boolean = { true },
) {
    // Get the start of today in UTC milliseconds to prevent selecting past dates.
    val todayUtcMillis = LocalDate.now(ZoneId.of("UTC"))
        .atStartOfDay(ZoneId.of("UTC"))
        .toInstant()
        .toEpochMilli()

    val selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            // A date is selectable if it's not in the past AND it passes the external validation (e.g., not a weekend).
            return utcTimeMillis >= todayUtcMillis && dateValidator(utcTimeMillis)
        }
    }

    val datePickerState = rememberDatePickerState(
        selectableDates = selectableDates
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 6.dp
        ) {
            // Reduce horizontal padding to give the calendar more space
            Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        headlineContentColor = MaterialTheme.colorScheme.onSurface,
                        weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                        subheadContentColor = MaterialTheme.colorScheme.onSurface,
                        yearContentColor = MaterialTheme.colorScheme.onSurface,
                        selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
                        dayContentColor = MaterialTheme.colorScheme.onSurface,
                        selectedDayContentColor = MaterialTheme.colorScheme.onSurface,
                        todayContentColor = MaterialTheme.colorScheme.onSurface,
                        todayDateBorderColor = MaterialTheme.colorScheme.onSurface,
                        selectedDayContainerColor = MaterialTheme.colorScheme.onPrimary
                    )
                )

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = MaterialTheme.colorScheme.onSurface)
                    }

                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")).toLocalDate()
                            onDateSelected(selectedDate.toString())
                        }
                        onDismiss()
                    }) {
                        Text("Aceptar", color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}
