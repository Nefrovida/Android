package com.example.nefrovida.domain.repository

import com.example.nefrovida.data.remote.dto.ChangePasswordDto
import com.example.nefrovida.data.remote.dto.UpdateProfileDto
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.UserProfile

interface ProfileRepository {
    suspend fun getMyProfile(): Result<UserProfile>

    suspend fun updateMyProfile(updateProfileDto: UpdateProfileDto): Result<UserProfile>

    suspend fun changePassword(changePasswordDto: ChangePasswordDto): Result<Unit>
}
