package com.example.sebwave.features.users.domain.usecases

import com.example.sebwave.features.users.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke() {
        repo.logout()
    }
}