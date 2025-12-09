package com.example.nefrovida.presentation.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.home.components.*

@Suppress("ktlint:standard:function-naming")
@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold { _ ->
        Box(
            modifier =
                modifier
                    .fillMaxSize(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                // Header with background image and logo
                HeaderSection()

                // Presentación section
                PresentacionSection()

                // Blue section with text and button
                BlueSection(navController = navController)

                Spacer(modifier = Modifier.height(20.dp))

                // Mision
                TextBox(
                    title = "Misión",
                    description =
                        "Brindar atención y apoyo multidisciplinario en la prevención, " +
                            "detección, control y tratamiento de personas con Enfermedad Renal Crónica, " +
                            "con o sin tratamiento sustitutivo de función renal (hemodiálisis, " +
                            "diálisis) y acompañamiento de protocolo de trasplante por medio de " +
                            "programas y acciones que contribuyan a mejorar su calidad de vida.",
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Vision
                TextBox(
                    title = "Visión",
                    description =
                        "Ser una organización autosustentable que promueve la prevención " +
                            "y detección oportuna en personas con factores de riesgo de la " +
                            "Enfermedad Renal Crónica (ERC), que se encuentran en situación " +
                            "vulnerable; con el fin de modificar positivamente la evolución " +
                            "natural y así disminuir la letalidad de la ERC.",
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Our Services section
                OurServicesSection()

                Spacer(modifier = Modifier.height(20.dp))

                // Contact section
                EncuentranosSection()

                Spacer(modifier = Modifier.height(10.dp))
            }

            // Fixed social media icons box at bottom right
            // SocialMediaBox(
            //    modifier = Modifier.align(Alignment.BottomEnd),
            // )
        }
    }
}
