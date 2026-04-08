package com.example.sebwave.features.users.data.datasources.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: Int,
    val username: String,
    val email: String,
    val role: String
)

data class LoginRequestDto(
    val email: String,
    val password: String
)

data class RegisterRequestDto(
    val username: String,
    val email: String,
    val password: String
)

data class AuthResponseDto(
    val user: UserDto,
    val token: String? = null
)