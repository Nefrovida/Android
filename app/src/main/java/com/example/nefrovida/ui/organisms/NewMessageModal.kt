package com.example.nefrovida.ui.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.remote.dto.SimpleForumInfo

@Composable
fun NewMessageModal(
    onDismiss: () -> Unit,
    onSend: (Int, String) -> Unit,
    forums: List<SimpleForumInfo>,
) {
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedForum by remember { mutableStateOf<SimpleForumInfo?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(24.dp),
        tonalElevation = 8.dp,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Forum,
                    contentDescription = null,
                    tint = Color(0xFF1E88E5),
                    modifier = Modifier.size(28.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Nuevo mensaje",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A5F),
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Forum Selector
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Foro",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E3A5F),
                        modifier = Modifier.padding(bottom = 6.dp),
                    )

                    OutlinedTextField(
                        value = selectedForum?.name ?: "",
                        onValueChange = { },
                        readOnly = true,
                        placeholder = {
                            Text(
                                "Selecciona un foro",
                                color = Color(0xFF9E9E9E),
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            Icon(
                                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xFF1E88E5),
                            )
                        },
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF1E88E5),
                                unfocusedBorderColor = Color(0xFFB0BEC5),
                                focusedContainerColor = Color(0xFFF5F9FF),
                                unfocusedContainerColor = Color.White,
                            ),
                        shape = RoundedCornerShape(12.dp),
                        interactionSource =
                            remember { MutableInteractionSource() }
                                .also { interactionSource ->
                                    LaunchedEffect(interactionSource) {
                                        interactionSource.interactions.collect {
                                            if (it is PressInteraction.Release) {
                                                expanded = !expanded
                                            }
                                        }
                                    }
                                },
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier =
                            Modifier
                                .fillMaxWidth(0.85f)
                                .background(Color.White, RoundedCornerShape(12.dp)),
                    ) {
                        if (forums.isEmpty()) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "No hay foros disponibles",
                                        color = Color(0xFF757575),
                                    )
                                },
                                onClick = { },
                            )
                        } else {
                            forums.forEach { forum ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            forum.name,
                                            color = Color(0xFF1E3A5F),
                                        )
                                    },
                                    onClick = {
                                        selectedForum = forum
                                        expanded = false
                                    },
                                )
                            }
                        }
                    }
                }

                // Message Input
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Mensaje",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E3A5F),
                        modifier = Modifier.padding(bottom = 6.dp),
                    )

                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            if (it.length <= 5000) text = it
                        },
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF1E88E5),
                                unfocusedBorderColor = Color(0xFFB0BEC5),
                                focusedLabelColor = Color(0xFF1E88E5),
                                cursorColor = Color(0xFF1E88E5),
                                focusedContainerColor = Color(0xFFF5F9FF),
                                unfocusedContainerColor = Color.White,
                            ),
                        placeholder = {
                            Text(
                                "Escribe tu mensaje aquí...",
                                color = Color(0xFF9E9E9E),
                            )
                        },
                        minLines = 4,
                        maxLines = 6,
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(160.dp),
                        shape = RoundedCornerShape(12.dp),
                    )

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Text(
                            "${text.length}/5000",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (text.length > 4500) Color(0xFFE53935) else Color(0xFF757575),
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (text.trim().isNotEmpty() && selectedForum != null) {
                        onSend(selectedForum!!.forumId, text)
                    }
                },
                enabled = text.trim().isNotEmpty() && selectedForum != null,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E88E5),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFE0E0E0),
                        disabledContentColor = Color(0xFF9E9E9E),
                    ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(48.dp),
                elevation =
                    ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 6.dp,
                        disabledElevation = 0.dp,
                    ),
            ) {
                Text(
                    "Enviar",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors =
                    ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFF757575),
                    ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(48.dp),
            ) {
                Text(
                    "Cancelar",
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
    )
}
