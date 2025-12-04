package com.example.nefrovida.presentation.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.RegisterRequest
import com.example.nefrovida.domain.repository.Result
import com.example.nefrovida.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(
        name: String,
        parentLastName: String,
        maternalLastName: String,
        phoneNumber: String,
        username: String,
        password: String,
        birthday: String,
        gender: String,
        curp: String
    ) {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            val request = RegisterRequest(
                name = name,
                parentLastName = parentLastName,
                maternalLastName = maternalLastName.ifBlank { null },
                phoneNumber = phoneNumber,
                username = username,
                password = password,
                birthday = birthday,
                gender = gender,
                curp = curp
            )
            when (val result = registerUseCase(request)) {
                is Result.Success -> {
                    _uiState.value = RegisterUiState.Success(result.data.message)
                }
                is Result.Error -> {
                    _uiState.value = RegisterUiState.Error(result.message)
                }
            }
        }
    }

    fun resetState() {
        _uiState.value = RegisterUiState.Idle
    }
}

sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    data class Success(val message: String) : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}
