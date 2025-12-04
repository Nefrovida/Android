package com.example.nefrovida.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.domain.repository.Result
import com.example.nefrovida.domain.usecase.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordResetViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PasswordResetDialogState())
    val uiState: StateFlow<PasswordResetDialogState> = _uiState.asStateFlow()

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