package com.example.nefrovida.presentation.screens.create_analysis

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CreateAnalysisScreen(
    viewModel: CreateAnalysisViewModel = hiltViewModel(),
    onAnalysisCreated: () -> Unit
) {
    val uiState = viewModel.uiState

    if (uiState.success) {
        SuccessDialog(
            onDismiss = {
                viewModel.onSuccessDialogDismissed()
                onAnalysisCreated()
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Text(
                text = "Formulario para crear análisis",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp).align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = uiState.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Nombre del análisis") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (uiState.name.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onNameChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3,
                trailingIcon = {
                    if (uiState.description.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onDescriptionChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.previousRequirements,
                onValueChange = viewModel::onPreviousRequirementsChange,
                label = { Text("Requisitos previos") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                trailingIcon = {
                    if (uiState.previousRequirements.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onPreviousRequirementsChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.generalCost,
                    onValueChange = viewModel::onGeneralCostChange,
                    label = { Text("Costo General") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    trailingIcon = {
                        if (uiState.generalCost.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onGeneralCostChange("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                            }
                        }
                    }
                )
                OutlinedTextField(
                    value = uiState.communityCost,
                    onValueChange = viewModel::onCommunityCostChange,
                    label = { Text("Costo Comunitario") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    trailingIcon = {
                        if (uiState.communityCost.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onCommunityCostChange("") }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            FilledTonalButton(
                onClick = { viewModel.createAnalysis() },
                enabled = !uiState.isLoading && uiState.name.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear")
            }

            uiState.error?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
private fun SuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cerrar")
                }
            }
        },
        text = { Text("El análisis se ha guardado con éxito", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.fillMaxWidth()) },
        confirmButton = {}
    )
}
