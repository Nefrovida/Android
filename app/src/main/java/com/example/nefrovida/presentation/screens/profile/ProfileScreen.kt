package com.example.nefrovida.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
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
import com.example.nefrovida.ui.theme.ErrorRed
import com.example.nefrovida.ui.theme.NavyBlue

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = NavyBlue,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                    ),
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (state.isLoading && state.profile == null) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = NavyBlue,
                )
            }

            state.profile?.let { profile ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 24.dp)
                            .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Profile Image
                    ProfileHeader()

                    Spacer(modifier = Modifier.height(32.dp))

                    // Profile Information Card
                    ProfileInfoCard(profile)

                    Spacer(modifier = Modifier.height(20.dp))

                    // Edit Profile Button
                    Button(
                        onClick = { showEditProfileDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyBlue,
                        ),
                        contentPadding = PaddingValues(vertical = 14.dp),
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Editar",
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "EDITAR MIS DATOS",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Password Card
                    PasswordInfoCard()

                    Spacer(modifier = Modifier.height(20.dp))

                    // Change Password Button
                    Button(
                        onClick = { showChangePasswordDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NavyBlue,
                        ),
                        contentPadding = PaddingValues(vertical = 14.dp),
                    ) {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = "Cambiar contraseña",
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "CAMBIAR CONTRASEÑA",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
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
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(NavyBlue.copy(alpha = 0.1f)),
    ) {
        AsyncImage(
            model = "https://via.placeholder.com/150",
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun ProfileInfoCard(profile: com.example.nefrovida.domain.model.UserProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "INFORMACIÓN PERSONAL",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = NavyBlue,
                letterSpacing = 0.5.sp,
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Name and Parent Last Name
            Row(Modifier.fillMaxWidth()) {
                InfoItem("Nombre(s)", profile.name, Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                InfoItem("Apellido Paterno", profile.parentLastName, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Maternal Last Name and Phone
            Row(Modifier.fillMaxWidth()) {
                InfoItem("Apellido Materno", profile.maternalLastName, Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                InfoItem("Teléfono", profile.phoneNumber, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun PasswordInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "SEGURIDAD",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = NavyBlue,
                letterSpacing = 0.5.sp,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Contraseña",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "************",
                    fontSize = 16.sp,
                    color = Color.Gray,
                )
            }
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
        Text(
            label,
            fontSize = 11.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.3.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
        )
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
    var errorMessage by remember { mutableStateOf("") }

    // Validation function
    fun validate(): Boolean {
        when {
            name.trim().isEmpty() -> {
                errorMessage = "El nombre es requerido"
                return false
            }
            name.trim().length > 50 -> {
                errorMessage = "El nombre debe tener como máximo 50 caracteres"
                return false
            }
            pLastName.trim().isEmpty() -> {
                errorMessage = "El apellido paterno es requerido"
                return false
            }
            pLastName.trim().length > 50 -> {
                errorMessage = "El apellido paterno debe tener como máximo 50 caracteres"
                return false
            }
            mLastName.trim().isEmpty() -> {
                errorMessage = "El apellido materno es requerido"
                return false
            }
            mLastName.trim().length > 50 -> {
                errorMessage = "El apellido materno debe tener como máximo 50 caracteres"
                return false
            }
            phone.trim().isEmpty() -> {
                errorMessage = "El teléfono es requerido"
                return false
            }
            !phone.trim().matches(Regex("^\\d{10,15}$")) -> {
                errorMessage = "El teléfono debe contener sólo dígitos y tener entre 10 y 15 caracteres"
                return false
            }
        }
        errorMessage = ""
        return true
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Editar Mis Datos",
                fontWeight = FontWeight.Bold,
                color = NavyBlue,
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        if (it.length <= 50) name = it
                        errorMessage = ""
                    },
                    label = { Text("Nombre(s)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = { Text("${name.length}/50") },
                )
                OutlinedTextField(
                    value = pLastName,
                    onValueChange = {
                        if (it.length <= 50) pLastName = it
                        errorMessage = ""
                    },
                    label = { Text("Apellido Paterno") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = { Text("${pLastName.length}/50") },
                )
                OutlinedTextField(
                    value = mLastName,
                    onValueChange = {
                        if (it.length <= 50) mLastName = it
                        errorMessage = ""
                    },
                    label = { Text("Apellido Materno") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = { Text("${mLastName.length}/50") },
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        if (it.matches(Regex("^\\d{0,15}$"))) phone = it
                        errorMessage = ""
                    },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    supportingText = { Text("${phone.length}/15 dígitos") },
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = ErrorRed,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validate()) {
                        onSave(name.trim(), pLastName.trim(), mLastName.trim(), phone.trim())
                    }
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = NavyBlue),
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text("GUARDAR")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed),
            ) {
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
    var errorMessage by remember { mutableStateOf("") }

    // Password validation regex matching backend: PASSWORD_REGEX
    val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d)(?=.*[#?!@\$%^&*\\-]).{8,}\$")

    fun validatePassword(): Boolean {
        when {
            newPassword.isEmpty() -> {
                errorMessage = "La nueva contraseña es requerida"
                return false
            }
            newPassword.length < 8 -> {
                errorMessage = "La contraseña debe tener al menos 8 caracteres"
                return false
            }
            !newPassword.contains(Regex("[A-Z]")) -> {
                errorMessage = "La contraseña debe tener al menos una letra mayúscula"
                return false
            }
            !newPassword.contains(Regex("\\d")) -> {
                errorMessage = "La contraseña debe tener al menos un número"
                return false
            }
            !newPassword.contains(Regex("[#?!@\$%^&*\\-]")) -> {
                errorMessage = "La contraseña debe tener al menos un carácter especial [#?!@\$%^&*-]"
                return false
            }
            !passwordRegex.matches(newPassword) -> {
                errorMessage = "La contraseña no cumple con los requisitos"
                return false
            }
            confirmPassword.isEmpty() -> {
                errorMessage = "Debe confirmar la nueva contraseña"
                return false
            }
            newPassword != confirmPassword -> {
                errorMessage = "Las contraseñas no coinciden"
                return false
            }
        }
        errorMessage = ""
        return true
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Cambiar Contraseña",
                fontWeight = FontWeight.Bold,
                color = NavyBlue,
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = {
                        newPassword = it
                        errorMessage = ""
                    },
                    label = { Text("Nueva contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (isNewPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (isNewPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { isNewPasswordVisible = !isNewPasswordVisible }) {
                            Icon(image, contentDescription = "Toggle visibility")
                        }
                    },
                    singleLine = true,
                    isError = errorMessage.isNotEmpty() && newPassword.isNotEmpty(),
                )
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        errorMessage = ""
                    },
                    label = { Text("Confirmar contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (isConfirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                            Icon(image, contentDescription = "Toggle visibility")
                        }
                    },
                    singleLine = true,
                    isError = errorMessage.isNotEmpty() && confirmPassword.isNotEmpty(),
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = ErrorRed,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp),
                    )
                } else {
                    Text(
                        text = "La contraseña debe tener al menos 8 caracteres, entre ellos, una mayúscula, un número y un carácter especial [#?!@\$%^&*-]",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        color = Color.Gray,
                        fontSize = 11.sp,
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validatePassword()) {
                        onSave(newPassword, confirmPassword)
                    }
                },
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = NavyBlue),
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text("GUARDAR")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = ErrorRed),
            ) {
                Text("CANCELAR")
            }
        },
    )
}