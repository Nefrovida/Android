package com.example.nefrovida.presentation.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDatePretty(dateString: String): String =
    try {
        val parsed = ZonedDateTime.parse(dateString)

        val formatter =
            DateTimeFormatter.ofPattern(
                "d MMM yyyy – hh:mm a",
                Locale("es", "ES"),
            )

        parsed.format(formatter)
    } catch (e: Exception) {
        dateString
    }

fun formatDatePretty2(
    dateString: String,
    hourString: String,
): String =
    try {
        val combined = "$dateString $hourString"

        val inputFormatter =
            java.time.format.DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm",
            )

        val dateTime = java.time.LocalDateTime.parse(combined, inputFormatter)

        val outputFormatter =
            java.time.format.DateTimeFormatter.ofPattern(
                "d MMM yyyy – hh:mm a",
                java.util.Locale("es", "ES"),
            )

        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        "$dateString $hourString"
    }
