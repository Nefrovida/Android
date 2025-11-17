package com.example.nefrovida.presentation.screens.home.components

// ... (otros imports)
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.ui.organisms.AppointmentCard

@Composable
fun AgendaList(
    onCardClick: (String) -> Unit, // Recibe el ID de la cita
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ){
        item {
            // TODO: Esto debe venir de un ViewModel
            // Datos de muestra actualizados
            AppointmentCard(
                name = "Oliver Queen",
                specialty = "Nefrología", // <-- Nuevo dato
                time = "10:30 AM",        // <-- Nuevo dato
                onClick = { onCardClick("1") } // "1" es un ID de muestra
            )
            Spacer(modifier = Modifier.height(12.dp)) // Espacio entre tarjetas
            AppointmentCard(
                name = "Barry Allen",
                specialty = "Cardiología", // <-- Nuevo dato
                time = "11:00 AM",       // <-- Nuevo dato
                onClick = { onCardClick("2") } // "2" es un ID de muestra
            )
        }
    }
}