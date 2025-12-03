package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

// DTO para recibir datos del perfil desde el backend
data class UserProfileDto(
    @SerializedName("user_id") val userId: String,
    @SerializedName("name") val name: String,
    @SerializedName("parent_last_name") val parentLastName: String,
    @SerializedName("maternal_last_name") val maternalLastName: String,
    @SerializedName("username") val username: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("role_name") val roleName: String,
)

// DTO para enviar la actualización del perfil
data class UpdateProfileDto(
    @SerializedName("name") val name: String?,
    @SerializedName("parent_last_name") val parentLastName: String?,
    @SerializedName("maternal_last_name") val maternalLastName: String?,
    @SerializedName("phone_number") val phoneNumber: String?,
)

// DTO para el cambio de contraseña
data class ChangePasswordDto(
    @SerializedName("newPassword") val newPassword: String,
    @SerializedName("confirmNewPassword") val confirmNewPassword: String,
)

// DTO para la respuesta de actualización de perfil
data class UpdateProfileResponse(
    val message: String,
    val data: UserProfileDto,
)

// DTO para la respuesta de cambio de contraseña
data class ChangePasswordResponse(
    val message: String,
)

// DTO para errores del backend
data class ErrorResponse(
    val message: String,
)
