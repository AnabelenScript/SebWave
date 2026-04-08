package com.example.sebwave.features.users.domain.repositories

import com.example.sebwave.features.users.domain.entities.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(username: String, email: String, password: String): Result<User>
    suspend fun logout()
}