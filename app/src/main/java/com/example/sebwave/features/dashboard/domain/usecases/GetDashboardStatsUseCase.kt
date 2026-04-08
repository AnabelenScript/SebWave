package com.example.sebwave.features.dashboard.domain.usecases

import com.example.sebwave.features.dashboard.domain.entities.DashboardStats
import com.example.sebwave.features.dashboard.domain.repositories.DashboardRepository
import javax.inject.Inject

class GetDashboardStatsUseCase @Inject constructor(
    private val repository: DashboardRepository
) {
    suspend operator fun invoke(): Result<DashboardStats> = repository.getDashboardStats()
}
