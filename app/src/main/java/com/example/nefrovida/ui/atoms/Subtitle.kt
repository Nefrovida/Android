package com.example.nefrovida.ui.atoms

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun Subtitle(
    text: String
) {
    Text(text, style = MaterialTheme.typography.titleMedium)
}