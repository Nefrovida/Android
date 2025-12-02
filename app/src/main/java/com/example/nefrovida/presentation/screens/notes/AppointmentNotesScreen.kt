package com.example.nefrovida.presentation.screens.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun AppointmentNotesScreen(viewModel: AppointmentNotesViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }

    when {
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.value.error}")
            }
        }

        else -> NotesList(state.value)
    }
}

@Composable
fun NotesList(state: AppointmentNotesUiState) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(state.appointmentNotes) { item ->
            AppointmentNoteCard(item)
        }
    }
}
