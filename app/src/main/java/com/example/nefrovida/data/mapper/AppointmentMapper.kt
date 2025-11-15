package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment

fun AppointmentDto.toDomain(): Appointment {
    // Separar fecha y hora
    val dateTimeParts = dateHour.split("T")       // ["2025-11-24", "20:16:02.976Z"]
    val date = dateTimeParts[0]                   // "2025-11-24"
    val time = dateTimeParts.getOrNull(1)?.substring(0,5) ?: "" // "20:16"

    return Appointment(
        id = id,
        name = name,
        date = date,
        time = time,
        type = type,
        duration = duration,
        link = link,
        place = place,
        status = status
    )
}