package com.example.nefrovida.presentation.screens.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nefrovida.data.remote.dto.ChangePasswordDto
import com.example.nefrovida.data.remote.dto.UpdateProfileDto
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.UserProfile
import com.example.nefrovida.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileState(
    val profile: UserProfile? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object ProfileUpdated : UiEvent()
    object PasswordChanged : UiEvent()
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getMyProfile()
    }

    fun getMyProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.getMyProfile()) {
                is Result.Success -> {
                    _state.value = ProfileState(profile = result.data, isLoading = false)
                }
                is Result.Error -> {
                    _state.value = ProfileState(isLoading = false, error = result.exception.message)
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.exception.message ?: "Error desconocido"))
                }
                is Result.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }
    }

    fun updateMyProfile(name: String, pLastName: String, mLastName: String, phone: String) {
        viewModelScope.launch {
            val dto = UpdateProfileDto(name, pLastName, mLastName, phone)
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.updateMyProfile(dto)) {
                is Result.Success -> {
                    _state.value = ProfileState(profile = result.data, isLoading = false)
                    _eventFlow.emit(UiEvent.ProfileUpdated)
                    _eventFlow.emit(UiEvent.ShowSnackbar("Perfil actualizado con éxito"))
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.exception.message ?: "Error al actualizar"))
                }
                is Result.Loading -> {}
            }
        }
    }

    fun changePassword(pass: String, confirmPass: String) {
        viewModelScope.launch {
            if (pass != confirmPass) {
                _eventFlow.emit(UiEvent.ShowSnackbar("Las contraseñas no coinciden"))
                return@launch
            }

            val dto = ChangePasswordDto(newPassword = pass, confirmNewPassword = confirmPass)
            _state.value = _state.value.copy(isLoading = true)
            when (val result = repository.changePassword(dto)) {
                is Result.Success -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.PasswordChanged)
                    _eventFlow.emit(UiEvent.ShowSnackbar("Contraseña cambiada con éxito"))
                }
                is Result.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.exception.message ?: "Error al cambiar contraseña"))
                }
                is Result.Loading -> {}
            }
        }
    }
}
