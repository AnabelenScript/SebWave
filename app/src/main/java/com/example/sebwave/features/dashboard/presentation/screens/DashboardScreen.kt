package com.example.sebwave.features.dashboard.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sebwave.features.dashboard.presentation.components.*
import com.example.sebwave.features.dashboard.presentation.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            DashboardBottomNavigation()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFAFAFA))
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            HeaderSection()
            Spacer(modifier = Modifier.height(24.dp))
            
            uiState.stats?.let { stats ->
                StatsSection(stats)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            IntersectionsHeader()
            Spacer(modifier = Modifier.height(16.dp))
            
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF4CAF50))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(uiState.semaphores) { semaphore ->
                        SemaphoreCard(semaphore = semaphore)
                    }
                }
            }
        }
    }
}
