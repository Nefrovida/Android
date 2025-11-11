package com.example.nefrovida.presentation.screens.forum

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.home.components.AgendaList

@Suppress("ktlint:standard:function-naming")

@Composable
fun ForumScreen(
    onBackClick: () -> Unit,
    navController : NavController,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🚧 En construcción 🚧",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineMedium,

            )

        //TODO: corregir a lo adecuado en su momento (lista de reportes de laboratorio)
        /*AgendaList(

            onCardClick = {
                //TODO: navegar al detalle del reporte
            }
        )*/
    }
    }

