package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name") val name: String,
    @SerializedName("parent_last_name") val parentLastName: String,
    @SerializedName("maternal_last_name") val maternalLastName: String?,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String,
    @SerializedName("birthday") val birthday: String, // "YYYY-MM-DD" or ISO
    @SerializedName("gender") val gender: String,   // "MALE" | "FEMALE" | "OTHER"
    @SerializedName("curp") val curp: String
)
