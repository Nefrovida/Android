package com.example.nefrovida.data.remote.dto

data class UserDto(
    val id: String,
    val username: String,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val role: String?
)

