package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.UserDto
import com.example.nefrovida.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        role = role
    )
}

