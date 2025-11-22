package com.example.nefrovida.presentation.utils

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
    // Parse the input ISO date
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val date = inputFormat.parse(dateString) ?: return false

    val calendar = Calendar.getInstance() // local timezone

    // Today (local)
    val today = calendar.clone() as Calendar

    // Tomorrow
    val tomorrow = calendar.clone() as Calendar
    tomorrow.add(Calendar.DAY_OF_YEAR, 1)

    // Four months from today
    val maxLimit = calendar.clone() as Calendar
    maxLimit.add(Calendar.MONTH, 4)

    val cDate = Calendar.getInstance()
    cDate.time = date

    // Must be >= tomorrow
    if (cDate.before(tomorrow)) return false

    // Must be <= 4 months ahead
    if (cDate.after(maxLimit)) return false

    return true
}
