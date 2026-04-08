package com.example.sebwave.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sebwave.features.dashboard.domain.entities.DashboardStats

@Composable
fun StatsSection(stats: DashboardStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Green Gradient Card
        Card(
            modifier = Modifier
                .weight(1.5f)
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF2E7D32), Color(0xFF1B5E20))
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    StatItem(
                        icon = Icons.Default.DirectionsCar,
                        value = stats.processedVehicles,
                        label = "Vehículos",
                        subLabel = "Procesados"
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    StatItem(
                        icon = Icons.Default.AccessTime,
                        value = "${stats.savedTimeHours}H",
                        label = "Tiempo ahorrado",
                        subLabel = "Hoy"
                    )
                }
            }
        }

        // Semaphores summary card
        Card(
            modifier = Modifier
                .weight(1f)
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${stats.activeSemaphores}/${stats.totalSemaphores}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Box(modifier = Modifier.size(8.dp, 2.dp).background(Color.Red))
                        Box(modifier = Modifier.size(8.dp, 2.dp).background(Color.Yellow))
                        Box(modifier = Modifier.size(8.dp, 2.dp).background(Color.Green))
                    }
                }
                Text("Semáforos en la zona", fontSize = 10.sp, color = Color.Gray)
                
                SemaphoreCountItem(Color.Red, stats.redSemaphores, "Rojos")
                SemaphoreCountItem(Color.Yellow, stats.yellowSemaphores, "Amarillos")
                SemaphoreCountItem(Color.Green, stats.greenSemaphores, "Verdes")
            }
        }
    }
}

@Composable
private fun StatItem(icon: ImageVector, value: String, label: String, subLabel: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                if (value.contains("K")) {
                   Text("K", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(bottom = 4.dp))
                }
            }
            Text(subLabel, color = Color.White.copy(alpha = 0.7f), fontSize = 10.sp)
        }
    }
}

@Composable
private fun SemaphoreCountItem(color: Color, count: Int, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(count.toString(), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}
