package com.example.sebwave.features.users.data.datasources.api

import com.example.sebwave.features.users.data.datasources.model.LoginRequestDto
import com.example.sebwave.features.users.data.datasources.model.AuthResponseDto
import com.example.sebwave.features.users.data.datasources.model.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): AuthResponseDto

    @POST("users/register")
    suspend fun register(
        @Body request: RegisterRequestDto
    ): AuthResponseDto
}