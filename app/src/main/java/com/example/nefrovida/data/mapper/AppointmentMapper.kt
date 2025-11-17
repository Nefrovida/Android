package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AppointmentDto
import com.example.nefrovida.domain.model.Appointment

fun AppointmentDto.toDomain(): Appointment {
    val dateTimeParts = dateHour.split("T")
    val date = dateTimeParts[0]
    val time = dateTimeParts.getOrNull(1)?.substring(0,5) ?: ""

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