package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nefrovida.ui.theme.NavyBlue
import com.example.nefrovida.ui.theme.TextGray

/**
 * A reusable dropdown component that works with any type T
 *
 * @param T The type of items in the dropdown (e.g., String, Int, custom data class)
 * @param label The label to display on the text field
 * @param selectedValue The currently selected value (can be null)
 * @param options The list of options to display in the dropdown
 * @param expanded Whether the dropdown menu is expanded
 * @param onExpandedChange Callback when the expanded state changes
 * @param onValueSelected Callback when a value is selected
 * @param displayText Function to convert an item of type T to display text
 * @param placeholder Text to show when no value is selected (defaults to "Seleccionar")
 * @param modifier Modifier for the component
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ReusableDropdown(
    label: String,
    selectedValue: T?,
    options: List<T>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueSelected: (T) -> Unit,
    displayText: (T) -> String,
    placeholder: String = "Seleccionar",
    modifier: Modifier = Modifier,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selectedValue?.let { displayText(it) } ?: placeholder,
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
            onDismissRequest = { onExpandedChange(false) },
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(displayText(item)) },
                    onClick = {
                        onValueSelected(item)
                        onExpandedChange(false)
                    },
                )
            }
        }
    }
}

/**
 * Convenience function for String dropdowns
 */
@Composable
fun ReusableStringDropdown(
    label: String,
    selectedValue: String?,
    options: List<String>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueSelected: (String) -> Unit,
    placeholder: String = "Seleccionar",
    modifier: Modifier = Modifier,
) {
    ReusableDropdown(
        label = label,
        selectedValue = selectedValue,
        options = options,
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        onValueSelected = onValueSelected,
        displayText = { it },
        placeholder = placeholder,
        modifier = modifier,
    )
}

/**
 * Convenience function for Int dropdowns
 */
@Composable
fun ReusableIntDropdown(
    label: String,
    selectedValue: Int?,
    options: List<Int>,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onValueSelected: (Int) -> Unit,
    displayText: (Int) -> String = { it.toString() },
    placeholder: String = "Seleccionar",
    modifier: Modifier = Modifier,
) {
    ReusableDropdown(
        label = label,
        selectedValue = selectedValue,
        options = options,
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        onValueSelected = onValueSelected,
        displayText = displayText,
        placeholder = placeholder,
        modifier = modifier,
    )
}
