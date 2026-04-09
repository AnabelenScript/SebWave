package com.example.sebwave.features.map.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sebwave.features.dashboard.domain.entities.SemaphoreStatus
import com.example.sebwave.features.map.domain.entities.SemaphoreMarker

@Composable
fun SemaphoreInfoCard(
    marker: SemaphoreMarker,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = when (marker.status) {
        SemaphoreStatus.CONECTADO    -> Color(0xFF4CAF50)
        SemaphoreStatus.DESCONECTADO -> Color(0xFF9E9E9E)
        SemaphoreStatus.FALLIDO      -> Color(0xFFF44336)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = marker.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = marker.street,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Estado
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(statusColor, shape = RoundedCornerShape(6.dp))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = marker.status.displayName, fontSize = 12.sp, color = Color.Gray)
                    Text(text = "Estado", fontSize = 10.sp, color = Color.LightGray)
                }

                // Congestión
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${marker.currentCongestion}%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = congestionColor(marker.currentCongestion)
                    )
                    Text(text = "Congestión", fontSize = 10.sp, color = Color.LightGray)
                }

                // Coordenadas
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "%.5f".format(marker.latitude),
                        fontSize = 11.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        text = "%.5f".format(marker.longitude),
                        fontSize = 11.sp,
                        color = Color.DarkGray
                    )
                    Text(text = "Coordenadas", fontSize = 10.sp, color = Color.LightGray)
                }
            }
        }
    }
}

private fun congestionColor(congestion: Int): Color = when {
    congestion >= 80 -> Color(0xFFF44336)
    congestion >= 50 -> Color(0xFFFF9800)
    else             -> Color(0xFF4CAF50)
}
