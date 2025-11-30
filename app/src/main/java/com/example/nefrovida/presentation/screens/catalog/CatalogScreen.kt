package com.example.nefrovida.presentation.screens.catalog

import AnalysisList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.presentation.screens.catalog.comps.AppointmentList
import com.example.nefrovida.ui.molecules.ResultsToggleButton

@Suppress("ktlint:standard:function-naming")
@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsState()
    var currentView by remember { mutableStateOf(CatalogViewType.APPOINTMENTS) }

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Error: ${state.error}")
            }
        }

        else -> {
            Column(modifier = Modifier.fillMaxSize()) {
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
                            text = "Consultas",
                            selected = currentView == CatalogViewType.APPOINTMENTS,
                            onClick = { currentView = CatalogViewType.APPOINTMENTS },
                            modifier = Modifier.weight(1f),
                        )

                        ResultsToggleButton(
                            text = "Laboratorio",
                            selected = currentView == CatalogViewType.LAB,
                            onClick = { currentView = CatalogViewType.LAB },
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
                when (currentView) {
                    CatalogViewType.APPOINTMENTS -> {
                        AppointmentList(
                            services = state.appointments,
                        )
                    }

                    CatalogViewType.LAB -> {
                        AnalysisList(
                            services = state.analysis,
                        )
                    }
                }
            }
        }
    }
}

enum class CatalogViewType {
    APPOINTMENTS,
    LAB,
}
