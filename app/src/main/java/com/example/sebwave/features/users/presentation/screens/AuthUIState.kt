package com.example.sebwave.features.users.presentation.screens

import com.example.sebwave.features.users.domain.entities.User

data class AuthUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)