package com.example.sebwave.features.dashboard.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sebwave.features.dashboard.domain.entities.*
import com.example.sebwave.features.dashboard.domain.usecases.GetDashboardStatsUseCase
import com.example.sebwave.features.dashboard.domain.usecases.GetSemaphoresUseCase
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
class DashboardViewModel @Inject constructor(
    private val getDashboardStatsUseCase: GetDashboardStatsUseCase,
    private val getSemaphoresUseCase: GetSemaphoresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            val statsResult = getDashboardStatsUseCase()
            val semaphoresResult = getSemaphoresUseCase()

            if (statsResult.isSuccess && semaphoresResult.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    stats = statsResult.getOrNull(),
                    semaphores = semaphoresResult.getOrDefault(emptyList())
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al cargar los datos del dashboard"
                )
            }
        }
    }
}
