package com.example.sebwave.features.dashboard.domain.repositories

import com.example.sebwave.features.dashboard.domain.entities.DashboardStats
import com.example.sebwave.features.dashboard.domain.entities.Semaphore

interface DashboardRepository {
    suspend fun getDashboardStats(): Result<DashboardStats>
    suspend fun getSemaphores(): Result<List<Semaphore>>
}
