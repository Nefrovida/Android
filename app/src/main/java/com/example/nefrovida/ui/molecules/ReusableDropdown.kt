package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nefrovida.ui.theme.NavyBlue
import com.example.nefrovida.ui.theme.TextGray

@Composable
fun ResuableDropdown(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<E>,
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = Modifier.weight(1f),
    ) {
        OutlinedTextField(
            value = value.ifBlank { label },
            readOnly = true,
            onValueChange = {},
            label = { Text(text = label) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NavyBlue,
                    unfocusedBorderColor = TextGray,
                    focusedLabelColor = NavyBlue,
                ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onExpandedChange,
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        appointmentType = type
                        typeExpanded = false
                    },
                )
            }
        }
    }
}
