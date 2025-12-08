package com.example.nefrovida.presentation.utils

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDatePretty(dateString: String): String =
    try {
        val parsedUtc = ZonedDateTime.parse(dateString)
        val localDateTime = parsedUtc.withZoneSameInstant(ZoneId.systemDefault())

        val formatter =
            DateTimeFormatter.ofPattern(
                "d MMM yyyy – hh:mm a",
                Locale("es", "ES"),
            )

        localDateTime.format(formatter)
    } catch (e: Exception) {
        dateString
    }

fun formatDatePretty2(
    dateString: String,
    hourString: String,
): String =
    try {
        val combined = "${dateString}T$hourString" // Combine using T for ISO standard, e.g., "2023-10-25T09:00:00"

        // Input formatter for a date and time WITHOUT timezone information
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val localDateTime = LocalDateTime.parse(combined, inputFormatter)

        // Output formatter with the desired AM/PM format
        val outputFormatter =
            DateTimeFormatter.ofPattern(
                "d MMM yyyy – hh:mm a",
                Locale("es", "ES"),
            )

        // Directly format the LocalDateTime without any timezone conversion
        localDateTime.format(outputFormatter)
    } catch (e: Exception) {
        "$dateString $hourString"
    }

fun formatTimeAmPm(timeString: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val outputFormatter = DateTimeFormatter.ofPattern("hh:mm a", Locale("es", "ES"))
        val time = LocalTime.parse(timeString, inputFormatter)
        time.format(outputFormatter)
    } catch (e: Exception) {
        timeString // Fallback to original string if parsing fails
    }
}
