package com.example.nefrovida.presentation.screens.login

data class PasswordResetDialogState(
    val username: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val usernameError: String? = null,
    val requestSuccess: Boolean = false,
)