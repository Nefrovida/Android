package com.example.nefrovida.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.laboratory.AnalysisHistoryScreen
import com.example.nefrovida.presentation.screens.notes.AppointmentNotesScreen
import com.example.nefrovida.ui.molecules.ResultsToggleButton

@Composable
fun PatientHistoryScreen(navController: NavController) {
    var currentView by remember { mutableStateOf(PatientView.ANALYSIS) }

    Column(modifier = Modifier.fillMaxSize()) {
        // --- TOGGLE SUPERIOR ---
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Row(
                modifier =
                    Modifier
                        .background(Color(0xFFF2F2F7), RoundedCornerShape(50))
                        .padding(4.dp)
                        .fillMaxWidth(),
            ) {
                ResultsToggleButton(
                    text = "Resultados",
                    selected = currentView == PatientView.ANALYSIS,
                    onClick = { currentView = PatientView.ANALYSIS },
                    modifier = Modifier.weight(1f),
                )

                ResultsToggleButton(
                    text = "Notas",
                    selected = currentView == PatientView.NOTES,
                    onClick = { currentView = PatientView.NOTES },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        // --- CONTENIDO ---
        when (currentView) {
            PatientView.ANALYSIS -> {
                AnalysisHistoryScreen(navController)
            }
            PatientView.NOTES -> {
                AppointmentNotesScreen()
            }
        }
    }
}

enum class PatientView {
    ANALYSIS,
    NOTES,
}
