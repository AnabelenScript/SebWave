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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.NotificationImportant
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.sebwave.core.ui.theme.primaryLight
import com.example.sebwave.core.ui.theme.trafficAmber
import com.example.sebwave.core.ui.theme.trafficRed
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

    val icon = when (alert.type) {
        AlertType.SIRENA_DETECTADA -> Icons.Outlined.NotificationImportant
        AlertType.SENSOR_DESCONECTADO -> Icons.Outlined.WarningAmber
        AlertType.ALTA_CONGESTION -> Icons.Outlined.DirectionsCar
    }

    val accentColor = when (alert.type) {
        AlertType.SIRENA_DETECTADA -> trafficRed
        AlertType.SENSOR_DESCONECTADO -> trafficAmber
        AlertType.ALTA_CONGESTION -> primaryLight
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onAlertClicked(context, alert) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Barra lateral de color por tipo
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(accentColor)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    // Icono con fondo circular sutil
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                accentColor.copy(alpha = 0.12f),
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = alert.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                                modifier = Modifier.weight(1f, fill = false)
                            )

                            // Etiqueta "Resuelta"
                            if (alert.status == AlertStatus.RESOLVED) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    color = Color(0xFFF5F5F5),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "Resuelta",
                                        modifier = Modifier.padding(
                                            horizontal = 8.dp,
                                            vertical = 2.dp
                                        ),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.Gray
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = alert.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Metadata: time + location
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.AccessTime,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = " ${alert.timeAgo}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            Text("|", color = Color.LightGray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = " ${alert.location}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Muestra un Toast y una notificación push al hacer click en una alerta.
 */
private fun onAlertClicked(context: Context, alert: Alert) {
    // Toast
    Toast.makeText(
        context,
        "⚠️ ${alert.title}: ${alert.description}",
        Toast.LENGTH_LONG
    ).show()

    // Notificación push
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
    // Verificar permiso en Android 13+
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
