package com.example.nefrovida.data.remote.api

import com.example.nefrovida.data.remote.dto.ChangePasswordDto
import com.example.nefrovida.data.remote.dto.ChangePasswordResponse
import com.example.nefrovida.data.remote.dto.UpdateProfileDto
import com.example.nefrovida.data.remote.dto.UpdateProfileResponse
import com.example.nefrovida.data.remote.dto.UserProfileDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApi {
    @GET("profile/me")
    suspend fun getMyProfile(): Response<UserProfileDto>

    @PUT("profile/me")
    suspend fun updateMyProfile(
        @Body updateProfileDto: UpdateProfileDto,
    ): Response<UpdateProfileResponse>

    @PUT("profile/change-password")
    suspend fun changePassword(
        @Body changePasswordDto: ChangePasswordDto,
    ): Response<ChangePasswordResponse>
}
