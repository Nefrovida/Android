package com.example.nefrovida.presentation.screens.labanalysis

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nefrovida.ui.atoms.Pill
import com.example.nefrovida.ui.molecule.PopupBox
import com.example.nefrovida.ui.organism.laboratory.LabAnalysisListContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabAnalysisScreen(
    viewModel: LabAnalysisViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {Text("Laboratory Analysis")}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            LabAnalysisListContainer(
                labAnalysisList = uiState.labAnalysisList,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onRetry = { viewModel.loadLabAnalysisList() },
                loadMoreItems = { page ->
                    viewModel.loadLabAnalysisList(page)
                }
            )
        }

    }
}