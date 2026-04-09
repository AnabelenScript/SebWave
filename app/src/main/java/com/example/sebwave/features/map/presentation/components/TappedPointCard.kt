package com.example.sebwave.features.map.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.osmdroid.util.GeoPoint

@Composable
fun TappedPointCard(
    point: GeoPoint,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color(0xFF2196F3),
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Coordenadas
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Punto marcado",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Lat: ${"%.6f".format(point.latitude)}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Lon: ${"%.6f".format(point.longitude)}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }

            // Botón cerrar
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = "Cerrar")
            }
        }
    }
}
