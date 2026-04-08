package com.example.sebwave.features.users.domain.usecases

import com.example.sebwave.features.users.domain.entities.User
import com.example.sebwave.features.users.domain.repositories.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(username: String, email: String, password: String): Result<User> {
        return repo.register(username, email, password)
    }
}