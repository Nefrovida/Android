package com.example.nefrovida.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.home.components.*

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold { paddingValues ->
        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(paddingValues),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
            ) {
                // Header with background image and logo
                HeaderSection()

                // Blue section with text and button
                BlueSection(navController = navController)

                // Our Services section
                OurServicesSection()

                // Contact section
                EncuentranosSection()
            }

            // Fixed social media icons box at bottom right
            SocialMediaBox(
                modifier = Modifier.align(Alignment.BottomEnd),
            )
        }
    }
}
