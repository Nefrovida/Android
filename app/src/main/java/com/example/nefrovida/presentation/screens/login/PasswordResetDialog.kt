package com.example.nefrovida.presentation.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nefrovida.ui.theme.*

@Composable
fun PasswordResetDialog(
    viewModel: PasswordResetViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    AlertDialog(
        onDismissRequest = {
            viewModel.resetState()
            onDismiss()
        },
        title = {
            Text(
                text = "Recuperar Contraseña",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = NavyBlue,
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Ingresa tu nombre de usuario. Un administrador procesará tu solicitud y cambiará tu contraseña.",
                    fontSize = 14.sp,
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = { viewModel.onUsernameChange(it) },
                    label = { Text("Usuario") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = uiState.usernameError != null,
                    supportingText = {
                        uiState.usernameError?.let {
                            Text(
                                text = it,
                                color = ErrorRed,
                            )
                        }
                    },
                    trailingIcon = {
                        if (uiState.username.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onUsernameChange("") }) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = "Clear username",
                                )
                            }
                        }
                    },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                    keyboardActions =
                        KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                viewModel.onResetPasswordClick()
                            },
                        ),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NavyBlue,
                            unfocusedBorderColor = TextGray,
                            focusedLabelColor = NavyBlue,
                        ),
                    enabled = !uiState.isLoading,
                )

                // Success message
                if (uiState.requestSuccess) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Si el usuario existe se ha enviado una solicitud a los administradores",
                        color = NefroGreen,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                    )
                }

                // Error message
                if (uiState.errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.errorMessage!!,
                        color = ErrorRed,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { viewModel.onResetPasswordClick() },
                enabled = !uiState.isLoading,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = NavyBlue,
                    ),
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = White,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text("Solicitar")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    viewModel.resetState()
                    onDismiss()
                },
                enabled = !uiState.isLoading,
            ) {
                Text(
                    text = "Cancelar",
                    color = NavyBlue,
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = White,
    )
}