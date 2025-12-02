package com.example.nefrovida.domain.model

data class ServiceItem(
    val id: Int,
    val name: String,
    val generalCost: Int,
    val communityCost: Int,
    val doctor: String? = null,
    val description: String? = null,
    val previousRequirements: String? = null,
)
