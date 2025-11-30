package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ResultsToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val background = if (selected) Color.White else Color(0xFFF2F2F7)
    val contentColor = if (selected) Color.Black else Color.DarkGray

    Surface(
        modifier =
            modifier
                .clip(RoundedCornerShape(50))
                .clickable { onClick() },
        color = background,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    ) {
        Box(
            modifier =
                Modifier
                    .padding(vertical = 10.dp, horizontal = 24.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
