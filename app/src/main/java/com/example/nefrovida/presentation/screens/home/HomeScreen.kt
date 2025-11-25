package com.example.nefrovida.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nefrovida.ui.organisms.NfBottomNavigationBar

@Suppress("ktlint:standard:function-naming")
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) { // TODO: Crear HomePage
    Scaffold { _ ->
        Box(
            modifier =
                modifier
                    .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "🚧 En construcción 🚧",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}
