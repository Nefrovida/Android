package com.example.nefrovida.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.nefrovida.ui.organisms.AppointmentCard

@Composable
fun AgendaList(onCardClick: (String) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
        //TODO: jalar el contenido de la base de datos
        items(15) { index ->
            AppointmentCard(
                name = "Oliver Queen",
                date = "2025-11-10",
                type = "PRESENCIAL",
                duration = 30,
                onClick = {onCardClick(index.toString())},
            )
        }
    }
}