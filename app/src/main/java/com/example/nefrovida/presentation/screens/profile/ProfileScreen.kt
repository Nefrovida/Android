package com.example.nefrovida.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short,
                    )
                }

                is UiEvent.ProfileUpdated -> {
                    showEditProfileDialog = false
                }

                is UiEvent.PasswordChanged -> {
                    showChangePasswordDialog = false
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF6A1B9A), // Color morado oscuro
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                    ),
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading && state.profile == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            state.profile?.let { profile ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ProfileHeader()
                    Spacer(modifier = Modifier.height(24.dp))
                    ProfileInfoCard(profile)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showEditProfileDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50),
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("EDITAR MIS DATOS")
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    PasswordInfoCard()
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showChangePasswordDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50),
                    ) {
                        Text("CAMBIAR CONTRASEÑA")
                    }
                }
            }

            if (showEditProfileDialog) {
                EditProfileDialog(
                    profile = state.profile!!,
                    onDismiss = { showEditProfileDialog = false },
                    onSave = { name, pLastName, mLastName, phone ->
                        viewModel.updateMyProfile(name, pLastName, mLastName, phone)
                    },
                    isLoading = state.isLoading,
                )
            }

            if (showChangePasswordDialog) {
                ChangePasswordDialog(
                    onDismiss = { showChangePasswordDialog = false },
                    onSave = { pass, confirmPass ->
                        viewModel.changePassword(pass, confirmPass)
                    },
                    isLoading = state.isLoading,
                )
            }
        }
    }
}

@Composable
fun ProfileHeader() {
    Box(contentAlignment = Alignment.BottomEnd) {
        AsyncImage(
            model = "https://via.placeholder.com/150", // Reemplazar con la URL real de la imagen
            contentDescription = "Foto de perfil",
            modifier =
                Modifier
                    .size(120.dp)
                    .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        FloatingActionButton(
            onClick = { /* TODO: Lógica para cambiar imagen */ },
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Editar imagen")
        }
    }
}

@Composable
fun ProfileInfoCard(profile: com.example.nefrovida.domain.model.UserProfile) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("MIS DATOS", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                InfoItem("Nombre(s)", profile.name, Modifier.weight(1f))
                InfoItem("Apellido Paterno", profile.parentLastName, Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            InfoItem("Apellido Materno", profile.maternalLastName)
        }
    }
}

@Composable
fun PasswordInfoCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("CONTRASEÑA", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("************")
        }
    }
}

@Composable
fun InfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun EditProfileDialog(
    profile: com.example.nefrovida.domain.model.UserProfile,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String) -> Unit,
    isLoading: Boolean,
) {
    var name by remember { mutableStateOf(profile.name) }
    var pLastName by remember { mutableStateOf(profile.parentLastName) }
    var mLastName by remember { mutableStateOf(profile.maternalLastName) }
    var phone by remember { mutableStateOf(profile.phoneNumber) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Mis Datos") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre(s)") })
                OutlinedTextField(value = pLastName, onValueChange = { pLastName = it }, label = { Text("Apellido Paterno") })
                OutlinedTextField(value = mLastName, onValueChange = { mLastName = it }, label = { Text("Apellido Materno") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") })
            }
        },
        confirmButton = {
            Button(onClick = { onSave(name, pLastName, mLastName, phone) }, enabled = !isLoading) {
                if (isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp)) else Text("GUARDAR")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCELAR")
            }
        },
    )
}

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    isLoading: Boolean,
) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isNewPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cambiar Contraseña") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("Nueva contraseña") },
                    visualTransformation = if (isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (isNewPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { isNewPasswordVisible = !isNewPasswordVisible }) {
                            Icon(image, "Toggle visibility")
                        }
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar nueva contraseña") },
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualtransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                            Icon(image, "Toggle visibility")
                        }
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "La contraseña debe tener al menos 8 caracteres, entre ellos, una mayúscula, un número y un carácter especial [#?!@\$%^&*-]",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(newPassword, confirmPassword) },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
            ) {
                // Color Rosa
                if (isLoading) CircularProgressIndicator(modifier = Modifier.size(24.dp)) else Text("GUARDAR CAMBIOS")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("CANCELAR")
            }
        },
    )
}
