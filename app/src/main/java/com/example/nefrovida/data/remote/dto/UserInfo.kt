package com.example.nefrovida.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("parent_last_name")
    val parentLastName: String,

    @SerializedName("maternal_last_name")
    val maternalLastName: String,

    @SerializedName("username")
    val username: String
) {
    fun getFullName(): String {
        return "$name $parentLastName $maternalLastName".trim()
    }
}
