package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.remote.dto.ChangePasswordDto
import com.example.nefrovida.data.remote.dto.UpdateProfileDto
import com.example.nefrovida.domain.model.UserProfile
import com.example.nefrovida.utils.Resource

interface ProfileRepository {
    suspend fun getMyProfile(): Resource<UserProfile>

    suspend fun updateMyProfile(updateProfileDto: UpdateProfileDto): Resource<UserProfile>

    suspend fun changePassword(changePasswordDto: ChangePasswordDto): Resource<Unit>
}
