package com.example.sebwave.features.users.domain.entities

enum class UserRole {
    REGULAR, ADMIN
}

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val role: UserRole = UserRole.REGULAR
)