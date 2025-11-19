package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.UserDto
import com.example.nefrovida.domain.model.User

fun UserDto.toDomain(): User =
    User(
        id = id,
        name = name,
        username = username,
        role = role,
    )
