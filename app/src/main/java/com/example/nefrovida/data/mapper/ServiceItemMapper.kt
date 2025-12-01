package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.AppointmentDto
import com.example.nefrovida.data.remote.dto.ServiceItemDto
import com.example.nefrovida.domain.model.ServiceItem

fun ServiceItemDto.toDomain(): ServiceItem =
    ServiceItem(
        id = id,
        name = name,
        generalCost = generalCost,
        communityCost = communityCost,
        doctor = doctor,
        description = description,
        previousRequirements = previousRequirements,
    )
