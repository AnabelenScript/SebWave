package com.example.sebwave.features.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sebwave.features.dashboard.domain.entities.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val stats: DashboardStats? = null,
    val semaphores: List<Semaphore> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            // TODO: Reemplazar con llamadas reales a la API
            /*
            val statsResult = dashboardRepository.getStats()
            val semaphoresResult = dashboardRepository.getSemaphores()
            ... logic for success/failure
            */

            // Simulación de datos según el esquema y la imagen
            val simulatedStats = DashboardStats(
                processedVehicles = "42.5K",
                savedTimeHours = 150,
                totalSemaphores = 128,
                activeSemaphores = 12,
                redSemaphores = 3,
                yellowSemaphores = 3,
                greenSemaphores = 3
            )

            val simulatedSemaphores = listOf(
                Semaphore(
                    id = 1,
                    name = "INT-001",
                    street = "Av. Reforma & Insurgentes",
                    maxCongestion = 0.9,
                    maxTimeGreenLight = 60,
                    status = SemaphoreStatus.CONECTADO,
                    latitude = 19.4326,
                    longitude = -99.1332,
                    currentCongestion = 45,
                    currentGreenTime = 32
                ),
                Semaphore(
                    id = 2,
                    name = "INT-0012",
                    street = "Av. Costa rica & Insurgentes",
                    maxCongestion = 0.8,
                    maxTimeGreenLight = 45,
                    status = SemaphoreStatus.FALLIDO,
                    latitude = 19.4350,
                    longitude = -99.1400,
                    currentCongestion = 38,
                    currentGreenTime = 12
                ),
                Semaphore(
                    id = 3,
                    name = "INT-02122",
                    street = "Av. Reforma & Insurgentes",
                    maxCongestion = 0.7,
                    maxTimeGreenLight = 50,
                    status = SemaphoreStatus.DESCONECTADO,
                    latitude = 19.4400,
                    longitude = -99.1500,
                    currentCongestion = 45,
                    currentGreenTime = 22
                )
            )

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                stats = simulatedStats,
                semaphores = simulatedSemaphores
            )
        }
    }
}
