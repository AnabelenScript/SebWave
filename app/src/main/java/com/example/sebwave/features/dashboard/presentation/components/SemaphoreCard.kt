package com.example.sebwave.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sebwave.core.ui.theme.*
import com.example.sebwave.features.dashboard.domain.entities.Semaphore
import com.example.sebwave.features.dashboard.domain.entities.SemaphoreStatus

@Composable
fun SemaphoreCard(semaphore: Semaphore) {
    val statusColor = when(semaphore.status) {
        SemaphoreStatus.CONECTADO -> trafficGreen
        SemaphoreStatus.FALLIDO -> trafficRed
        SemaphoreStatus.DESCONECTADO -> trafficAmber
    }
    
    val statusLabel = when(semaphore.status) {
        SemaphoreStatus.CONECTADO -> "Óptimo"
        SemaphoreStatus.FALLIDO -> "Saturado"
        SemaphoreStatus.DESCONECTADO -> "Advertencia"
    }

    val statusContainerColor = when(semaphore.status) {
        SemaphoreStatus.CONECTADO -> trafficGreenContainer
        SemaphoreStatus.FALLIDO -> trafficRedContainer
        SemaphoreStatus.DESCONECTADO -> trafficAmberContainer
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(statusColor))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = semaphore.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = scrimLight
                        )
                    )
                }
                Surface(
                    color = statusContainerColor,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = statusLabel,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
            }
            
            Text(
                text = semaphore.street,
                color = statusColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Densidad vehicular", 
                        color = scrimLight.copy(alpha = 0.6f), 
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${semaphore.currentCongestion}%", 
                        fontWeight = FontWeight.Bold,
                        color = scrimLight
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.AccessTime, 
                        contentDescription = null, 
                        tint = statusColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = "Verde activo", 
                            fontSize = 8.sp, 
                            color = scrimLight.copy(alpha = 0.6f)
                        )
                        Text(
                            text = "${semaphore.currentGreenTime}s",
                            fontWeight = FontWeight.Bold,
                            color = scrimLight
                        )
                    }
                }
            }
        }
    }
}
