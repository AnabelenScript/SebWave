package com.example.sebwave.features.alerts.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.NotificationImportant
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sebwave.core.ui.theme.trafficRed
import com.example.sebwave.core.ui.theme.trafficAmber
import com.example.sebwave.core.ui.theme.primaryLight
import com.example.sebwave.features.alerts.domain.entities.AlertStats

@Composable
fun AlertStatsRow(
    stats: AlertStats,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard(
            count = stats.total,
            label = "Total",
            icon = Icons.Default.Circle,
            iconColor = Color.Black,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            count = stats.emergencies,
            label = "Emergencias",
            icon = Icons.Outlined.NotificationImportant,
            iconColor = trafficRed,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            count = stats.failures,
            label = "Fallos",
            icon = Icons.Outlined.WarningAmber,
            iconColor = trafficAmber,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            count = stats.congestions,
            label = "Congestión",
            icon = Icons.Outlined.ErrorOutline,
            iconColor = primaryLight,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    count: Int,
    label: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = count.toString(),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                fontSize = 9.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}
