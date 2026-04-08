package com.example.sebwave.features.dashboard.domain.usecases

import com.example.sebwave.features.dashboard.domain.entities.Semaphore
import com.example.sebwave.features.dashboard.domain.repositories.DashboardRepository
import javax.inject.Inject

class GetSemaphoresUseCase @Inject constructor(
    private val repository: DashboardRepository
) {
    suspend operator fun invoke(): Result<List<Semaphore>> = repository.getSemaphores()
}
