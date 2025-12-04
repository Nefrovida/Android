package com.example.nefrovida.presentation.screens.register

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nefrovida.R
import java.util.Calendar

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Form State
    var name by remember { mutableStateOf("") }
    var parentLastName by remember { mutableStateOf("") }
    var maternalLastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("MALE") }
    var curp by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    // Date Picker Logic
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog =
        DatePickerDialog(
            context,
            { _: DatePicker, y: Int, m: Int, d: Int ->
                val formattedMonth = (m + 1).toString().padStart(2, '0')
                val formattedDay = d.toString().padStart(2, '0')
                birthday = "$y-$formattedMonth-$formattedDay"
            },
            year,
            month,
            day,
        )

    LaunchedEffect(uiState) {
        when (uiState) {
            is RegisterUiState.Error -> {
                Toast.makeText(context, (uiState as RegisterUiState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    if (uiState is RegisterUiState.Success) {
        AlertDialog(
            onDismissRequest = {
                onNavigateBack()
                viewModel.resetState()
            },
            containerColor = Color.White,
            titleContentColor = Color(0xFF1E3A8A),
            textContentColor = Color(0xFF1F2937),
            shape = RoundedCornerShape(24.dp),
            title = {
                Text(
                    text = "Registro Exitoso",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            },
            text = {
                Text(
                    text = (uiState as RegisterUiState.Success).message,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onNavigateBack()
                        viewModel.resetState()
                    },
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E3A8A),
                        ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Aceptar", fontWeight = FontWeight.Bold, color = Color.White)
                }
            },
        )
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
                        Color(0xFFA8C5DD),
                        Color(0xFF1E3A8A),
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Surface(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White.copy(alpha = 0.95f),
                shadowElevation = 8.dp,
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
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

                    Text(
                        text = "Registro de Paciente",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1F2937),
                    )

                    // Form Fields
                    RegisterTextField(value = name, onValueChange = { name = it }, label = "Nombre *")
                    RegisterTextField(value = parentLastName, onValueChange = { parentLastName = it }, label = "Apellido Paterno *")
                    RegisterTextField(value = maternalLastName, onValueChange = { maternalLastName = it }, label = "Apellido Materno")

                    RegisterTextField(
                        value = phoneNumber,
                        onValueChange = { if (it.length <= 10) phoneNumber = it.filter { char -> char.isDigit() } },
                        label = "Teléfono *",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    )

                    // Birthday
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = birthday,
                            onValueChange = { },
                            label = { Text("Fecha de Nacimiento *") },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false, // Disable typing, force click
                            readOnly = true,
                            trailingIcon = {
                                Icon(Icons.Default.DateRange, contentDescription = "Select Date", tint = Color.Gray)
                            },
                            colors =
                            OutlinedTextFieldDefaults.colors(
                                disabledTextColor = Color.Black,
                                disabledBorderColor = Color.LightGray,
                                disabledLabelColor = Color.Gray,
                                disabledContainerColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(12.dp),
                        )
                        // Make the disabled text field clickable
                        Box(modifier = Modifier.matchParentSize().clickable { datePickerDialog.show() })
                    }

                    // Gender
                    GenderSelector(selectedGender = gender, onGenderSelected = { gender = it })

                    RegisterTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = "Usuario *",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña *") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1E3A8A),
                            unfocusedBorderColor = Color.LightGray,
                        ),
                    )

                    RegisterTextField(
                        value = curp,
                        onValueChange = { curp = it.uppercase() },
                        label = "CURP *",
                        placeholder = "ABCD123456HDFXYZ01",
                    )
                    Text(
                        text = "Clave Única de Registro de Población (18 caracteres)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Start),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            viewModel.register(
                                name,
                                parentLastName,
                                maternalLastName,
                                phoneNumber,
                                username,
                                password,
                                birthday,
                                gender,
                                curp,
                            )
                        },
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(50),
                        colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E3A8A),
                        ),
                        enabled = uiState !is RegisterUiState.Loading,
                    ) {
                        if (uiState is RegisterUiState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Registrar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("¿Ya tienes cuenta? ", color = Color.Gray, fontSize = 14.sp)
                        TextButton(onClick = { onNavigateBack() }) {
                            Text("Inicia sesión", color = Color(0xFF2563EB), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    placeholder: String? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder =
        if (placeholder !=
            null
        ) {
            {
                Text(
                    placeholder,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                )
            }
        } else {
            null
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors =
        OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1E3A8A),
            unfocusedBorderColor = Color.LightGray,
        ),
        keyboardOptions = keyboardOptions,
        singleLine = true,
    )
}

@Composable
fun GenderSelector(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("MALE" to "Masculino", "FEMALE" to "Femenino", "OTHER" to "Otro")

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = genders.find { it.first == selectedGender }?.second ?: selectedGender,
            onValueChange = {},
            label = { Text("Género *") },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            readOnly = true,
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, "Select Gender")
            },
            colors =
            OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.LightGray,
                disabledLabelColor = Color.Gray,
                disabledContainerColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(12.dp),
        )
        // Overlay box to capture clicks
        Box(modifier = Modifier.matchParentSize().clickable { expanded = true })

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.8f), // Adjust width as needed
        ) {
            genders.forEach { (key, label) ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onGenderSelected(key)
                        expanded = false
                    },
                )
            }
        }
    }
}
