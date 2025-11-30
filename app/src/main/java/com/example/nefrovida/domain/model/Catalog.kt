package com.example.nefrovida.domain.model

data class CatalogItem(
    val id: Int,
    val name: String,
    val description: String,
    val generalCost: Int,
    val internalCost: Int,
)

// data class Analysis(
//    val id: Int,
//    val name: String,
//    val date: String,
//    val time: String,
// )
