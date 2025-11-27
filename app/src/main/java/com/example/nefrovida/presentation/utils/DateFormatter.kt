package com.example.nefrovida.presentation.utils

import android.util.Log
import java.text.SimpleDateFormat
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
    
    // Parse date string - handle both date-only and ISO datetime formats
    val date: Date?
    if (dateString.contains("T")) {
        // ISO datetime format - parse as UTC then convert to local
        val formats = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
        )
        date = formats.firstNotNullOfOrNull { pattern ->
            try {
                SimpleDateFormat(pattern, Locale.getDefault())
                    .apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }.parse(dateString)
            } catch (e: Exception) {
                null
            }
        }
    } else {
        // Date-only format (yyyy-MM-dd) - parse as local date
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            date = sdf.parse(dateString)
        } catch (e: Exception) {
            date = null
        }
    }
    
    if (date == null) return false // no format matched → invalid date

    // Get current date at midnight (local timezone)
    val now = Calendar.getInstance()
    now.set(Calendar.HOUR_OF_DAY, 0)
    now.set(Calendar.MINUTE, 0)
    now.set(Calendar.SECOND, 0)
    now.set(Calendar.MILLISECOND, 0)

    // Tomorrow at midnight (local timezone)
    val tomorrow = now.clone() as Calendar
    tomorrow.add(Calendar.DAY_OF_YEAR, 1)

    // Four months from today at midnight (local timezone)
    val maxLimit = now.clone() as Calendar
    maxLimit.add(Calendar.MONTH, 4)

    // Parse the selected date and normalize to midnight local time
    val cDate = Calendar.getInstance()
    cDate.time = date
    // Normalize to midnight for date-only comparison
    cDate.set(Calendar.HOUR_OF_DAY, 0)
    cDate.set(Calendar.MINUTE, 0)
    cDate.set(Calendar.SECOND, 0)
    cDate.set(Calendar.MILLISECOND, 0)

    // Compare dates (not times)
    // Date must be >= tomorrow (at least tomorrow)
    if (cDate.before(tomorrow)) {
        Log.d("checkValidDate", "Date is before tomorrow: ${cDate.time} < ${tomorrow.time}")
        return false
    }
    // Date must be <= maxLimit (within 4 months)
    if (cDate.after(maxLimit)) {
        Log.d("checkValidDate", "Date is after max limit: ${cDate.time} > ${maxLimit.time}")
        return false
    }

    Log.d("checkValidDate", "date was valid: $date")
    return true
}
