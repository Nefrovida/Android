package com.example.nefrovida.ui.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.WeekDay
import java.time.format.TextStyle
import java.util.Locale

@Suppress("ktlint:standard:function-naming")
@Composable
fun DayItem(
    day: WeekDay,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val dayNumberBackground =
        if (isSelected) {
            MaterialTheme.colorScheme.secondary
        } else {
            Color.Transparent
        }

    val dayNumberTextColor =
        if (isSelected) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onSurface
        }

    Column(
        modifier =
            Modifier
                .clickable(enabled = true) { onClick() }
                .padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text =
                day.date.dayOfWeek
                    .getDisplayName(TextStyle.SHORT, Locale("es", "ES"))
                    .replace(".", ""),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        Box(
            modifier =
                Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(dayNumberBackground),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = dayNumberTextColor,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            )
        }
    }
}
