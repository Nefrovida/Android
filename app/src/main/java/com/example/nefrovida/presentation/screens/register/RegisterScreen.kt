package com.example.nefrovida.presentation.screens.register

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var parentLastName by remember { mutableStateOf("") }
    var maternalLastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") } // Simple text for now, should be DatePicker
    var gender by remember { mutableStateOf("MALE") } // Default
    var curp by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        when (uiState) {
            is RegisterUiState.Success -> {
                Toast.makeText(context, (uiState as RegisterUiState.Success).message, Toast.LENGTH_LONG).show()
                onNavigateBack()
                viewModel.resetState()
            }
            is RegisterUiState.Error -> {
                Toast.makeText(context, (uiState as RegisterUiState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registro de Paciente") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = parentLastName,
                onValueChange = { parentLastName = it },
                label = { Text("Apellido Paterno") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = maternalLastName,
                onValueChange = { maternalLastName = it },
                label = { Text("Apellido Materno (Opcional)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Correo Electrónico (Usuario)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = birthday,
                onValueChange = { birthday = it },
                label = { Text("Fecha de Nacimiento (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )
            // Gender Selection (Simple Dropdown or RadioButtons could be better)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Género:")
                Button(onClick = { gender = "MALE" }, colors = ButtonDefaults.buttonColors(containerColor = if (gender == "MALE") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("M") }
                Button(onClick = { gender = "FEMALE" }, colors = ButtonDefaults.buttonColors(containerColor = if (gender == "FEMALE") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("F") }
                Button(onClick = { gender = "OTHER" }, colors = ButtonDefaults.buttonColors(containerColor = if (gender == "OTHER") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)) { Text("Other") }
            }

            OutlinedTextField(
                value = curp,
                onValueChange = { curp = it },
                label = { Text("CURP") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.register(
                        name, parentLastName, maternalLastName, phoneNumber, username, password, birthday, gender, curp
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is RegisterUiState.Loading
            ) {
                if (uiState is RegisterUiState.Loading) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Text("Registrar")
                }
            }
        }
    }
}
