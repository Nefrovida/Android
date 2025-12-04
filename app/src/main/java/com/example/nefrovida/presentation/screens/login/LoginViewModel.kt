package com.example.nefrovida.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.nefrovida.domain.repository.Result
import com.example.nefrovida.domain.repository.UserPreferencesRepository
import com.example.nefrovida.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null, errorMessage = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null, errorMessage = null) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick() {
        if (!validateInputs()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result =
                    loginUseCase(
                        username = _uiState.value.email,
                        password = _uiState.value.password,
                    )

                when (result) {
                    is Result.Success -> {
                        userPreferencesRepository.saveUserId(result.data.id)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                loginSuccess = true,
                                user = result.data,
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
        var isValid = true

        if (currentState.email.isBlank()) {
            _uiState.update { it.copy(emailError = "El usuario no puede estar vacío") }
            isValid = false
        }

        if (currentState.password.isBlank()) {
            _uiState.update { it.copy(passwordError = "La contraseña no puede estar vacía") }
            isValid = false
        }

        return isValid
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
