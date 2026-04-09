package com.example.sebwave.features.alerts.presentation.components

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.sebwave.core.ui.theme.*
import com.example.sebwave.features.alerts.domain.entities.Alert
import com.example.sebwave.features.alerts.domain.entities.AlertStatus
import com.example.sebwave.features.alerts.domain.entities.AlertType

private const val CHANNEL_ID = "sebwave_alerts"
private const val CHANNEL_NAME = "Alertas SebWave"

@Composable
fun AlertItem(
    alert: Alert,
    onResolve: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val accentColor = when (alert.type) {
        AlertType.SIRENA_DETECTADA -> trafficRed
        AlertType.SENSOR_DESCONECTADO -> trafficAmber
        AlertType.ALTA_CONGESTION -> primaryLight
    }

    val accentContainerColor = when (alert.type) {
        AlertType.SIRENA_DETECTADA -> trafficRedContainer
        AlertType.SENSOR_DESCONECTADO -> trafficAmberContainer
        AlertType.ALTA_CONGESTION -> primaryContainerLight
    }

    val typeLabel = when (alert.type) {
        AlertType.SIRENA_DETECTADA -> "Emergencia"
        AlertType.SENSOR_DESCONECTADO -> "Fallo"
        AlertType.ALTA_CONGESTION -> "Congestión"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onAlertClicked(context, alert) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Dot + Title + Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(accentColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = alert.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = scrimLight,
                            fontSize = 16.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Surface(
                    color = if (alert.status == AlertStatus.RESOLVED)
                        surfaceVariantLight else accentContainerColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (alert.status == AlertStatus.RESOLVED) "Resuelta" else typeLabel,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (alert.status == AlertStatus.RESOLVED)
                            onSurfaceVariantLight else accentColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = alert.description,
                color = if (alert.status == AlertStatus.RESOLVED) Color.Gray else scrimLight.copy(alpha = 0.7f),
                modifier = Modifier.fillMaxWidth(),
                lineHeight = 20.sp,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            HorizontalDivider(color = Color(0xFFF5F5F5), thickness = 1.dp)
            
            Spacer(modifier = Modifier.height(12.dp))

            // Footer: Location and Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Location
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(0.6f)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = alert.location,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Time
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        tint = accentColor.copy(alpha = 0.8f),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = alert.timeAgo,
                        color = scrimLight.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

private fun onAlertClicked(context: Context, alert: Alert) {
    Toast.makeText(
        context,
        "⚠️ ${alert.title}: ${alert.description}",
        Toast.LENGTH_LONG
    ).show()

    createNotificationChannel(context)
    showNotification(context, alert)
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Notificaciones de alertas del sistema SebWave"
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}

private fun showNotification(context: Context, alert: Alert) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    val iconRes = when (alert.type) {
        AlertType.SIRENA_DETECTADA -> android.R.drawable.ic_dialog_alert
        AlertType.SENSOR_DESCONECTADO -> android.R.drawable.stat_notify_error
        AlertType.ALTA_CONGESTION -> android.R.drawable.ic_dialog_info
    }

    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(iconRes)
        .setContentTitle(alert.title)
        .setContentText(alert.description)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("${alert.description}\n📍 ${alert.location}\n⏱ ${alert.timeAgo}")
        )
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .build()

    NotificationManagerCompat.from(context).notify(alert.id, notification)
}
