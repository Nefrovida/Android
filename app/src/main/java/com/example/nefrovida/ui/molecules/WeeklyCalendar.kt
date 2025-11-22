package com.example.nefrovida.ui.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nefrovida.ui.atoms.DayItem
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Suppress("ktlint:standard:function-naming")
@Composable
fun WeeklyCalendarView(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    dayContent: @Composable (day: WeekDay, isSelected: Boolean) -> Unit =
        { day, isSelected ->
            DayItem(
                day = day,
                isSelected = isSelected,
                onClick = { onDateSelected(day.date) },
            )
        },
) {
    val startDate = remember { YearMonth.now().minusMonths(100).atStartOfMonth() }
    val endDate = remember { YearMonth.now().plusMonths(100).atEndOfMonth() }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val calendarState =
        rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = selectedDate,
            firstDayOfWeek = firstDayOfWeek,
        )
    val visibleMonth by remember {
        derivedStateOf {
            val firstDay =
                calendarState.firstVisibleWeek.days
                    .first()
                    .date
            val monthName = firstDay.month.getDisplayName(TextStyle.FULL, Locale("es", "ES"))
            monthName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = visibleMonth,
                style =
                    MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        WeekCalendar(
            state = calendarState,
            modifier =
                Modifier
                    .padding(top = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 12.dp, horizontal = 8.dp),
            dayContent = { day ->
                val isSelected = day.date == selectedDate
                dayContent(day, isSelected)
            },
        )
    }
}
