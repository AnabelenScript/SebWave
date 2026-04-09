package com.example.sebwave.features.alerts.data.repositories

import com.example.sebwave.features.alerts.domain.entities.Alert
import com.example.sebwave.features.alerts.domain.entities.AlertStatus
import com.example.sebwave.features.alerts.domain.entities.AlertStats
import com.example.sebwave.features.alerts.domain.entities.AlertType
import com.example.sebwave.features.alerts.domain.repositories.AlertRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAlertRepository @Inject constructor() : AlertRepository {
    private val _alerts = MutableStateFlow(
        listOf(
            Alert(
                1, AlertType.SIRENA_DETECTADA, "Sirena detectada",
                "Patrulla detectada aproximándose por Av. Insurgentes", "Hace 2 minutos",
                "Intersección Av. Insurgentes & Calle Central", AlertStatus.ACTIVE
            ),
            Alert(
                2, AlertType.SENSOR_DESCONECTADO, "Dispositivo desconectado",
                "Semáforo en Av. Insurgentes no responde. Modo degradado activado.", "Hace 2 minutos",
                "Intersección Av. Insurgentes & Calle Central", AlertStatus.ACTIVE
            ),
            Alert(
                3, AlertType.SENSOR_DESCONECTADO, "Dispositivo desconectado",
                "Semáforo en Av. Insurgentes no responde. Modo degradado activado.", "Hace 2 minutos",
                "Intersección Av. Insurgentes & Calle Central", AlertStatus.RESOLVED
            ),
            Alert(
                4, AlertType.ALTA_CONGESTION, "Congestión muy elevada",
                "Congestión vehicular > 85% detectada durante los últimos 15 minutos", "Hace 15 minutos",
                "Intersección Periférico & San Jerónimo", AlertStatus.ACTIVE
            )
        )
    )

    override fun getAlerts(): Flow<List<Alert>> = _alerts.asStateFlow()

    override fun getAlertStats(): Flow<AlertStats> = _alerts.map { alerts ->
        AlertStats(
            total = alerts.size,
            emergencies = alerts.count { it.type == AlertType.SIRENA_DETECTADA },
            failures = alerts.count { it.type == AlertType.SENSOR_DESCONECTADO },
            congestions = alerts.count { it.type == AlertType.ALTA_CONGESTION }
        )
    }

    override suspend fun resolveAlert(alertId: Int) {
        _alerts.value = _alerts.value.map {
            if (it.id == alertId) it.copy(status = AlertStatus.RESOLVED) else it
        }
    }
}
