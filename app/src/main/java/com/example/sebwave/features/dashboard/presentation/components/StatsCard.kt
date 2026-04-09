package com.example.sebwave.features.dashboard.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sebwave.R
import com.example.sebwave.core.ui.theme.*
import com.example.sebwave.features.dashboard.domain.entities.DashboardStats

@Composable
fun StatsSection(stats: DashboardStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier
                .weight(1.4f)
                .height(210.dp),
            shape = RoundedCornerShape(28.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colorStops = arrayOf(
                                0.25f to Color(0xFF2FA600),
                                1.0f to Color(0xFF113C00)
                            ),
                            center = Offset(0f, 0f),
                            radius = 900f
                        )
                    )
                    .padding(20.dp)
            ) {
                Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
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

        // Card Derecha: Resumen de semáforos (Fondo Blanco)
        Card(
            modifier = Modifier
                .weight(1f)
                .height(210.dp),
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
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        fontSize = 28.sp,
                        letterSpacing = (-1).sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",

                        modifier = Modifier
                            .size(32.dp)
                            .offset(y = (-1).dp)
                    )
                }
                Text(
                    text = "Semáforos en la zona", 
                    fontSize = 11.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = (-1).sp
                )
                
                // Usando los colores semánticos del tema: trafficRed, trafficAmber, trafficGreen
                SemaphoreCountItem(trafficRed, stats.redSemaphores, "Rojo")
                SemaphoreCountItem(trafficAmber, stats.yellowSemaphores, "Amarillo")
                SemaphoreCountItem(trafficGreen, stats.greenSemaphores, "Verde")
            }
        }
    }
}

@Composable
private fun StatItem(icon: ImageVector, value: String, label: String, subLabel: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(36.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)

            }
            Text(subLabel, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp)
        }
    }
}

@Composable
private fun SemaphoreCountItem(color: Color, count: Int, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                count.toString(), 
                color = if (color == trafficAmber) Color.Black else Color.White, 
                fontSize = 14.sp, 
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label, 
            fontSize = 15.sp, 
            fontWeight = FontWeight.Bold, 
            color = Color.Black
        )
    }
}
