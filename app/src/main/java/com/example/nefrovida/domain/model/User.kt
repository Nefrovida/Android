package com.example.nefrovida.domain.model

data class User(
    val id: String,
    val email: String,
    val name: String,
    val token: String? = null
)

