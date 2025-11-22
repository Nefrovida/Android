package com.example.nefrovida.ui.molecules

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.nefrovida.ui.atoms.DayItem
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth

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
    val currentDate = remember { LocalDate.now() }
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

    WeekCalendar(
        state = calendarState,
        modifier = modifier,
        dayContent = { day ->
            val isSelected = day.date == selectedDate
            dayContent(day, isSelected)
        },
    )
}
