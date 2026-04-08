package com.example.sebwave.features.dashboard.data.datasources.model

import com.google.gson.annotations.SerializedName

data class SemaphoreDto(
    val id: Int,
    val name: String,
    @SerializedName("max_congestion") val maxCongestion: Double,
    @SerializedName("max_time_green_light") val maxTimeGreenLight: Int,
    val status: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("current_congestion") val currentCongestion: Int? = null,
    @SerializedName("current_green_time") val currentGreenTime: Int? = null,
    @SerializedName("street_name") val streetName: String? = null
)

data class DashboardStatsDto(
    @SerializedName("processed_vehicles") val processedVehicles: String,
    @SerializedName("saved_time_hours") val savedTimeHours: Int,
    @SerializedName("total_semaphores") val totalSemaphores: Int,
    @SerializedName("active_semaphores") val activeSemaphores: Int,
    @SerializedName("red_semaphores") val redSemaphores: Int,
    @SerializedName("yellow_semaphores") val yellowSemaphores: Int,
    @SerializedName("green_semaphores") val greenSemaphores: Int
)
