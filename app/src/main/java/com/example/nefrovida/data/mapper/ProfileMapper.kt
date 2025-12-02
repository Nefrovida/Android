package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.UserProfileDto
import com.example.nefrovida.domain.model.UserProfile

fun UserProfileDto.toDomain(): UserProfile =
    UserProfile(
        userId = this.userId,
        name = this.name,
        parentLastName = this.parentLastName,
        maternalLastName = this.maternalLastName,
        username = this.username,
        phoneNumber = this.phoneNumber,
        roleName = this.roleName,
    )
