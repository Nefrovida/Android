package com.example.nefrovida.domain.model

data class User(
    val id: String,
    val username: String,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val role: String?
)

