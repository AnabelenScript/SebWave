package com.example.sebwave.features.alerts.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sebwave.features.alerts.domain.entities.Alert
import com.example.sebwave.features.alerts.domain.entities.AlertStats
import com.example.sebwave.features.alerts.domain.entities.AlertStatus
import com.example.sebwave.features.alerts.domain.repositories.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AlertUiState(
    val alerts: List<Alert> = emptyList(),
    val stats: AlertStats = AlertStats(0, 0, 0, 0),
    val isLoading: Boolean = false,
    val selectedFilter: AlertFilter = AlertFilter.ALL
)

enum class AlertFilter {
    ALL, ACTIVE, RESOLVED
}

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val repository: AlertRepository
) : ViewModel() {

    private val _selectedFilter = MutableStateFlow(AlertFilter.ALL)

    val uiState: StateFlow<AlertUiState> = combine(
        repository.getAlerts(),
        repository.getAlertStats(),
        _selectedFilter
    ) { alerts, stats, filter ->
        val filteredAlerts = when (filter) {
            AlertFilter.ALL -> alerts
            AlertFilter.ACTIVE -> alerts.filter { it.status == AlertStatus.ACTIVE }
            AlertFilter.RESOLVED -> alerts.filter { it.status == AlertStatus.RESOLVED }
        }
        AlertUiState(
            alerts = filteredAlerts,
            stats = stats,
            selectedFilter = filter
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = AlertUiState(isLoading = true)
    )

    fun setFilter(filter: AlertFilter) {
        _selectedFilter.value = filter
    }

    fun resolveAlert(alertId: Int) {
        viewModelScope.launch {
            repository.resolveAlert(alertId)
        }
    }
}
