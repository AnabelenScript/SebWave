package com.example.sebwave.features.alerts.domain.entities

enum class AlertType(val displayName: String) {
    SIRENA_DETECTADA("Sirena detectada"),
    SENSOR_DESCONECTADO("Sensor desconectado"),
    ALTA_CONGESTION("Alta congestión")
}

enum class AlertStatus {
    ACTIVE, RESOLVED
}

data class Alert(
    val id: Int,
    val type: AlertType,
    val title: String,
    val description: String,
    val timeAgo: String,
    val location: String,
    val status: AlertStatus,
    val semaphoreId: Int? = null
)

data class AlertStats(
    val total: Int,
    val emergencies: Int,
    val failures: Int,
    val congestions: Int
)
