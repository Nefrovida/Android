package com.example.nefrovida.presentation.screens.forum

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ForumScreen(
    navController: NavController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Simplemente un marcador de posición
        Text(
            text = "Pantalla del Foro",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}