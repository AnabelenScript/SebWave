package com.example.sebwave.features.alerts.presentation.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sebwave.features.alerts.presentation.components.AlertFilterTabs
import com.example.sebwave.features.alerts.presentation.components.AlertItem
import com.example.sebwave.features.alerts.presentation.components.AlertStatsRow
import com.example.sebwave.features.alerts.presentation.components.AlertsHeaderSection
import com.example.sebwave.features.alerts.presentation.viewmodel.AlertFilter
import com.example.sebwave.features.alerts.presentation.viewmodel.AlertViewModel

@Composable
fun AlertScreen(
    viewModel: AlertViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Solicitar permiso de notificaciones en Android 13+
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { /* Se maneja silenciosamente */ }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header (logo + badge "En línea")
        item {
            AlertsHeaderSection()
        }

        // Estadísticas
        item {
            AlertStatsRow(stats = uiState.stats)
        }

        // Filtros (Todas / Activas / Resueltas)
        item {
            AlertFilterTabs(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = { viewModel.setFilter(it) },
                counts = mapOf(
                    AlertFilter.ALL to uiState.stats.total,
                    AlertFilter.ACTIVE to uiState.stats.emergencies + uiState.stats.failures + uiState.stats.congestions,
                    AlertFilter.RESOLVED to (uiState.stats.total - (uiState.stats.emergencies + uiState.stats.failures + uiState.stats.congestions))
                )
            )
        }congestions

        // Lista de alertas
        items(uiState.alerts) { alert ->
            AlertItem(
                alert = alert,
                onResolve = { viewModel.resolveAlert(alert.id) }
            )
        }

        // Espaciado inferior para el bottom nav
        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}
