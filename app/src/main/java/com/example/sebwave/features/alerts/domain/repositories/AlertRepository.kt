package com.example.sebwave.features.alerts.domain.repositories

import com.example.sebwave.features.alerts.domain.entities.Alert
import com.example.sebwave.features.alerts.domain.entities.AlertStats
import kotlinx.coroutines.flow.Flow

interface AlertRepository {
    fun getAlerts(): Flow<List<Alert>>
    fun getAlertStats(): Flow<AlertStats>
    suspend fun resolveAlert(alertId: Int)
}
