package com.example.nefrovida.data.repository

import com.example.nefrovida.data.mapper.toDomain
import com.example.nefrovida.data.remote.api.ProfileApi
import com.example.nefrovida.data.remote.dto.ChangePasswordDto
import com.example.nefrovida.data.remote.dto.UpdateProfileDto
import com.example.nefrovida.domain.common.Result
import com.example.nefrovida.domain.model.UserProfile
import com.example.nefrovida.domain.repository.ProfileRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class ProfileRepositoryImpl
    @Inject
    constructor(
        private val api: ProfileApi,
    ) : ProfileRepository {
        override suspend fun getMyProfile(): Result<UserProfile> =
            try {
                val response = api.getMyProfile()
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!.toDomain())
                } else {
                    Result.Error(Exception("HTTP ${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }

        override suspend fun updateMyProfile(updateProfileDto: UpdateProfileDto): Result<UserProfile> =
            try {
                val response = api.updateMyProfile(updateProfileDto)
                if (response.isSuccessful && response.body() != null) {
                    Result.Success(response.body()!!.data.toDomain())
                } else {
                    Result.Error(Exception("HTTP ${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }

        override suspend fun changePassword(changePasswordDto: ChangePasswordDto): Result<Unit> =
            try {
                val response = api.changePassword(changePasswordDto)
                if (response.isSuccessful) {
                    Result.Success(Unit)
                } else {
                    Result.Error(Exception("HTTP ${response.code()}: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.Error(e)
            }
    }