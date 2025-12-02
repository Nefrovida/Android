package com.example.nefrovida.domain.model

data class UserProfile(
    val userId: String,
    val name: String,
    val parentLastName: String,
    val maternalLastName: String,
    val username: String,
    val phoneNumber: String,
    val roleName: String,
)
