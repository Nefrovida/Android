package com.example.nefrovida.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.nefrovida.presentation.navigation.Screen
import com.example.nefrovida.domain.model.Appointments
import com.example.nefrovida.ui.organisms.AppointmentCard


@Composable
fun AgendaList(appointmentList: List<Appointments>,
               onCardClick: (Appointments) -> Unit){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
        //TODO: jalar el contenido de la base de datos
        items(items = appointmentList,
            key = {it.appointmentId},
            ) { appointment ->
            AppointmentCard(
                appointment = appointment,
                onClick = {onCardClick(appointment)},
            )
        }
    }
}