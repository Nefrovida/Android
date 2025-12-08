package com.example.nefrovida.presentation.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*

fun formatDateToDDMMYYYY(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    val date = inputFormat.parse(dateString)!!
    return outputFormat.format(date)
}

fun checkValidDate(dateString: String): Boolean {
    Log.d("checkValidDate", "checking date: $dateString")
    val formats =
        listOf(
            "yyyy-MM-dd",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
        )

    val date =
        formats.firstNotNullOfOrNull { pattern ->
            try {
                SimpleDateFormat(pattern, Locale.getDefault())
                    .apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }.parse(dateString)
            } catch (e: Exception) {
                null
            }
        } ?: return false // no format matched → invalid date

    // Normalize all dates to midnight local time for comparison (ignore time component)
    val now = Calendar.getInstance()
    now.set(Calendar.HOUR_OF_DAY, 0)
    now.set(Calendar.MINUTE, 0)
    now.set(Calendar.SECOND, 0)
    now.set(Calendar.MILLISECOND, 0)

    // Tomorrow at midnight
    val tomorrow = now.clone() as Calendar
    tomorrow.add(Calendar.DAY_OF_YEAR, 1)

    // Four months from today at midnight
    val maxLimit = now.clone() as Calendar
    maxLimit.add(Calendar.MONTH, 4)

    // Parse selected date and normalize to midnight for date-only comparison
    val cDate = Calendar.getInstance()
    cDate.time = date
    cDate.set(Calendar.HOUR_OF_DAY, 0)
    cDate.set(Calendar.MINUTE, 0)
    cDate.set(Calendar.SECOND, 0)
    cDate.set(Calendar.MILLISECOND, 0)

    Log.d("checkValidDate", "Selected date: ${cDate.time}, Tomorrow: ${tomorrow.time}")

    if (cDate.before(now)) {
        Log.d("checkValidDate", "Date is before tomorrow")
        return false
    }
    if (cDate.after(maxLimit)) {
        Log.d("checkValidDate", "Date is after max limit")
        return false
    }

    Log.d("checkValidDate", "date was valid: $date")
    return true
}
