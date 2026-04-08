package com.example.sebwave.features.dashboard.domain.entities

enum class SemaphoreStatus(val displayName: String) {
    CONECTADO("Conectado"),
    DESCONECTADO("Desconectado"),
    FALLIDO("Fallido")
}

data class Semaphore(
    val id: Int,
    val name: String,
    val street: String,
    val maxCongestion: Double,
    val maxTimeGreenLight: Int,
    val status: SemaphoreStatus,
    val latitude: Double,
    val longitude: Double,
    val currentCongestion: Int, // Porcentaje de densidad vehicular
    val currentGreenTime: Int   // Segundos de verde activo
)

enum class AlertType(val displayName: String) {
    SIRENA_DETECTADA("Sirena detectada"),
    SENSOR_DESCONECTADO("Sensor desconectado"),
    ALTA_CONGESTION("Alta congestión")
}

data class AlertHistory(
    val id: Int,
    val type: AlertType,
    val semaphoreId: Int?
)

data class DashboardStats(
    val processedVehicles: String,
    val savedTimeHours: Int,
    val totalSemaphores: Int,
    val activeSemaphores: Int,
    val redSemaphores: Int,
    val yellowSemaphores: Int,
    val greenSemaphores: Int
)
