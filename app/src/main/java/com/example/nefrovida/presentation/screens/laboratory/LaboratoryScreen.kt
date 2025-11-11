package com.example.nefrovida.presentation.screens.laboratory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.home.components.AgendaList

@Suppress("ktlint:standard:function-naming")

@Composable
fun LaboratoryScreen(
    onBackClick: () -> Unit,
    navController : NavController,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize()
    )  {
        //TODO: corregir a lo adecuado en su momento (lista de reportes de laboratorio)
        AgendaList(
            onCardClick = {
                navController.navigate("reportDetail")
            }
        )
    }
}