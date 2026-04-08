package com.example.sebwave.features.dashboard.data.mappers

import com.example.sebwave.features.dashboard.data.datasources.model.DashboardStatsDto
import com.example.sebwave.features.dashboard.data.datasources.model.SemaphoreDto
import com.example.sebwave.features.dashboard.domain.entities.DashboardStats
import com.example.sebwave.features.dashboard.domain.entities.Semaphore
import com.example.sebwave.features.dashboard.domain.entities.SemaphoreStatus

fun DashboardStatsDto.toDomain(): DashboardStats {
    return DashboardStats(
        processedVehicles = processedVehicles,
        savedTimeHours = savedTimeHours,
        totalSemaphores = totalSemaphores,
        activeSemaphores = activeSemaphores,
        redSemaphores = redSemaphores,
        yellowSemaphores = yellowSemaphores,
        greenSemaphores = greenSemaphores
    )
}

fun SemaphoreDto.toDomain(): Semaphore {
    return Semaphore(
        id = id,
        name = name,
        street = streetName ?: "Calle desconocida",
        maxCongestion = maxCongestion,
        maxTimeGreenLight = maxTimeGreenLight,
        status = when (status.lowercase()) {
            "conectado" -> SemaphoreStatus.CONECTADO
            "fallido" -> SemaphoreStatus.FALLIDO
            else -> SemaphoreStatus.DESCONECTADO
        },
        latitude = latitude,
        longitude = longitude,
        currentCongestion = currentCongestion ?: 0,
        currentGreenTime = currentGreenTime ?: 0
    )
}
