package com.example.nefrovida.presentation.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.repository.AuthRepositoryImpl
import com.example.nefrovida.di.NetworkModule
import com.example.nefrovida.domain.repository.Result
import com.example.nefrovida.domain.usecase.ForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PasswordResetViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(PasswordResetDialogState())
    val uiState: StateFlow<PasswordResetDialogState> = _uiState.asStateFlow()

    // Initialize dependencies
    private val authApiService = NetworkModule.provideAuthApiService(application)
    private val authRepository = AuthRepositoryImpl(authApiService)
    private val forgotPasswordUseCase = ForgotPasswordUseCase(authRepository)

    fun onUsernameChange(username: String) {
        _uiState.update { it.copy(username = username, usernameError = null, errorMessage = null) }
    }

    fun onResetPasswordClick() {
        if (!validateInputs()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result = forgotPasswordUseCase(_uiState.value.username)

                when (result) {
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                requestSuccess = true,
                            )
                        }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message,
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error desconocido",
                    )
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val currentState = _uiState.value

        if (currentState.username.isBlank()) {
            _uiState.update { it.copy(usernameError = "El usuario no puede estar vacío") }
            return false
        }

        return true
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun resetState() {
        _uiState.value = PasswordResetDialogState()
    }
}