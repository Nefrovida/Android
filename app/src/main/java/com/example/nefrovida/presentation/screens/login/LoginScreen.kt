package com.example.nefrovida.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nefrovida.R
import com.example.nefrovida.ui.theme.BackgroundGradientEnd
import com.example.nefrovida.ui.theme.BackgroundGradientStart
import com.example.nefrovida.ui.theme.ErrorRed
import com.example.nefrovida.ui.theme.NavyBlue
import com.example.nefrovida.ui.theme.TextGray
import com.example.nefrovida.ui.theme.White

@Suppress("ktlint:standard:function-naming")
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
    onLoginSuccess: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var showPasswordResetDialog by remember { mutableStateOf(false) }

    // Navigate on successful login
    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            uiState.user?.let { user ->
                onLoginSuccess(user.id)
            }
        }
    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .background(
                brush =
                Brush.verticalGradient(
                    colors =
                    listOf(
                        BackgroundGradientStart,
                        BackgroundGradientEnd,
                    ),
                ),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
    ) {
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.nefrovidalogologin),
                contentDescription = "Logo NefroVida",
                modifier =
                Modifier
                    .heightIn(max = 100.dp)
                    .size(400.dp),
            )
            Spacer(modifier = Modifier.height(40.dp))

            // Card de Login
            Card(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors =
                CardDefaults.cardColors(
                    containerColor = White,
                ),
                elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 8.dp,
                ),
            ) {
                Column(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Título
                    Text(
                        text = stringResource(R.string.welcome),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyBlue,
                        modifier = Modifier.padding(bottom = 24.dp),
                    )

                    // Campo de Usuario
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        label = { Text(stringResource(R.string.username)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = uiState.emailError != null,
                        supportingText = {
                            uiState.emailError?.let {
                                Text(
                                    text = it,
                                    color = ErrorRed,
                                )
                            }
                        },
                        trailingIcon = {
                            if (uiState.email.isNotEmpty()) {
                                IconButton(onClick = { viewModel.onEmailChange("") }) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = "Clear email",
                                    )
                                }
                            }
                        },
                        keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions =
                        KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) },
                        ),
                        colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NavyBlue,
                            unfocusedBorderColor = TextGray,
                            focusedLabelColor = NavyBlue,
                        ),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de Contraseña
                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = { Text(stringResource(R.string.password)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation =
                        if (uiState.isPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        isError = uiState.passwordError != null,
                        supportingText = {
                            uiState.passwordError?.let {
                                Text(
                                    text = it,
                                    color = ErrorRed,
                                )
                            }
                        },
                        trailingIcon = {
                            IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                                Icon(
                                    imageVector =
                                    if (uiState.isPasswordVisible) {
                                        Icons.Filled.VisibilityOff
                                    } else {
                                        Icons.Filled.Visibility
                                    },
                                    contentDescription =
                                    if (uiState.isPasswordVisible) {
                                        "Hide password"
                                    } else {
                                        "Show password"
                                    },
                                )
                            }
                        },
                        keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions =
                        KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                                viewModel.onLoginClick()
                            },
                        ),
                        colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NavyBlue,
                            unfocusedBorderColor = TextGray,
                            focusedLabelColor = NavyBlue,
                        ),
                    )

                    // Olvidaste tu contraseña
                    TextButton(
                        onClick = { showPasswordResetDialog = true },
                        modifier = Modifier.align(Alignment.End),
                    ) {
                        Text(
                            text = stringResource(R.string.forgot_password),
                            color = NavyBlue,
                            fontSize = 14.sp,
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botón de Iniciar Sesión
                    Button(
                        onClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            viewModel.onLoginClick()
                        },
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !uiState.isLoading,
                        shape = RoundedCornerShape(25.dp),
                        colors =
                        ButtonDefaults.buttonColors(
                            containerColor = NavyBlue,
                            contentColor = White,
                        ),
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = White,
                                strokeWidth = 2.dp,
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.login_button),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    // Mensaje de error
                    uiState.errorMessage?.let { errorMessage ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = errorMessage,
                            color = ErrorRed,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Crear cuenta
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = stringResource(R.string.new_user),
                    color = White,
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.height(48.dp))
        }

        // Password Reset Dialog
        if (showPasswordResetDialog) {
            PasswordResetDialog(
                onDismiss = { showPasswordResetDialog = false }
            )
        }
    }
}
