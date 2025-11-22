package com.example.nefrovida.ui.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.WeekDay

@Suppress("ktlint:standard:function-naming")
@Composable
fun DayItem(
    day: WeekDay,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val background =
        if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }

    val textColor =
        if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }

    Box(
        modifier =
            Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(background)
                .clickable(enabled = true) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
