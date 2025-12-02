package com.example.nefrovida.presentation.screens.catalog.comps

import androidx.compose.runtime.Composable
import com.example.nefrovida.data.remote.dto.ServiceItemDto

@Composable
fun AppointmentList(services: List<ServiceItemDto>) {
    androidx.compose.foundation.lazy.LazyColumn {
        items(services.size) { index ->
            val item = services[index]

            AppointmentCard(item)
        }
    }
}
