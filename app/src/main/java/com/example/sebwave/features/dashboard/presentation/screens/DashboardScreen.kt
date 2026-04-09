package com.example.sebwave.features.dashboard.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sebwave.features.dashboard.presentation.components.*
import com.example.sebwave.features.dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var showAddDialog by remember { mutableStateOf(false) }

    // Modal para agregar semáforo
    if (showAddDialog) {
        AddSemaphoreDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { name, serial, density, greenTime, lat, lon ->
                // TODO: Conectar con API para crear semáforo
                showAddDialog = false
                Toast.makeText(
                    context,
                    "✅ Semáforo \"$name\" listo para conectar (S/N: $serial, Lat: ${"%.4f".format(lat ?: 0.0)}, Lon: ${"%.4f".format(lon ?: 0.0)})",
                    Toast.LENGTH_LONG
                ).show()
            }
        )
    }

    // Usamos LazyColumn para que TODA la pantalla sea scrollable como una sola unidad
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA)),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderSection()
        }
        
        uiState.stats?.let { stats ->
            item {
                StatsSection(stats)
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            IntersectionsHeader(onAddSemaphore = { showAddDialog = true })
        }
        
        if (uiState.isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF4CAF50))
                }
            }
        } else {
            items(uiState.semaphores) { semaphore ->
                SemaphoreCard(semaphore = semaphore)
            }
        }

    }
}
