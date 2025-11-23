package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Suppress("ktlint:standard:function-naming")
@Composable
fun Dialog(
    title: String,
    text: String,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    confirmButtonColor: Color = MaterialTheme.colorScheme.onSurface,
    dismissButtonColor: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color = MaterialTheme.colorScheme.primary,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = containerColor,
        title = {
            Text(
                text = title,
                color = titleColor,
                modifier = Modifier.padding(4.dp),
                fontSize = 20.sp,
            )
        },
        text = {
            Text(
                text = text,
                color = titleColor,
                modifier = Modifier.padding(4.dp),
                fontSize = 16.sp,
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                modifier =
                    Modifier
                        .padding(4.dp),
            ) {
                Text(
                    confirmText,
                    color = dismissButtonColor,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier =
                    Modifier
                        .padding(4.dp),
            ) {
                Text(
                    dismissText,
                    color = dismissButtonColor,
                )
            }
        },
    )
}
