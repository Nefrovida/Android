package com.example.nefrovida.domain.model

/**
 * Modelo para representar un día en el selector
 */
data class DayItem(
    val dayOfWeek: String, // ej: "lun", "mar", "mié"
    val dayNumber: Int, // ej: 13, 14, 15
    val isSelected: Boolean = false,
)
