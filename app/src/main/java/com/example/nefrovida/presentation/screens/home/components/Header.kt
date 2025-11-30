package com.example.nefrovida.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nefrovida.R
import com.example.nefrovida.presentation.navigation.Screen
import com.example.nefrovida.ui.theme.NavyBlue
import com.example.nefrovida.ui.theme.White

@Composable
fun HeaderSection() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(100.dp),
    ) {
        // Background image covering the whole section
        Image(
            painter = painterResource(id = R.drawable.home_header_image),
            contentDescription = "Imagen de fondo del Inicio",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        // Logo and name overlay (to the left)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text =
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                            append("NEFR")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("O")
                        }
                        withStyle(style = SpanStyle(color = Color.Green)) {
                            append("Vida")
                        }
                    },
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = White,
            )
        }
    }
}

@Composable
fun BlueSection(navController: NavController) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(NavyBlue)
                .padding(24.dp),
    ) {
        Column {
            Text(
                text =
                    "En NefroVida A.C. estamos comprometidos con la salud de " +
                        "nuestros pacientes. Ofrecemos servicios especializados en " +
                        "la detección, prevención y tratamiento de la Enfermedad Renal " +
                        "Crónica, diseñados para proteger tu salud renal y mejorar tu " +
                        "calidad de vida.",
                color = White,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screen.Catalog.route) {
                            popUpTo(navController.graph.startDestinationId) { 
                                saveState = true 
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = White,
                            contentColor = NavyBlue,
                        ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    Text(
                        text = "Ver Catalogo",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun SocialMediaBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(16.dp),
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = com.example.nefrovida.ui.theme.LightGray,
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // Facebook icon
                IconButton(
                    onClick = { /* TODO: Open Facebook */ },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "Facebook",
                        tint = NavyBlue,
                        modifier = Modifier.size(24.dp),
                    )
                }

                // Instagram icon
                IconButton(
                    onClick = { /* TODO: Open Instagram */ },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Instagram",
                        tint = NavyBlue,
                        modifier = Modifier.size(24.dp),
                    )
                }

                // Website icon
                IconButton(
                    onClick = { /* TODO: Open Website */ },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = "Website",
                        tint = NavyBlue,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}

