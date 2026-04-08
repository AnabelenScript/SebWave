package com.example.sebwave.features.dashboard.data.datasources.remote

import com.example.sebwave.features.dashboard.data.datasources.model.DashboardStatsDto
import com.example.sebwave.features.dashboard.data.datasources.model.SemaphoreDto
import retrofit2.Response
import retrofit2.http.GET

interface DashboardApiService {
    @GET("dashboard/stats")
    suspend fun getStats(): Response<DashboardStatsDto>

    @GET("semaphores")
    suspend fun getSemaphores(): Response<List<SemaphoreDto>>
}
