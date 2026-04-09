package com.example.sebwave.features.alerts.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sebwave.features.alerts.presentation.viewmodel.AlertFilter

@Composable
fun AlertFilterTabs(
    selectedFilter: AlertFilter,
    onFilterSelected: (AlertFilter) -> Unit,
    counts: Map<AlertFilter, Int>,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color(0xFFEEEEEE),
        shape = RoundedCornerShape(24.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterTabItem(
                label = "Todas",
                count = counts[AlertFilter.ALL] ?: 0,
                isSelected = selectedFilter == AlertFilter.ALL,
                onClick = { onFilterSelected(AlertFilter.ALL) },
                modifier = Modifier.weight(1f)
            )
            FilterTabItem(
                label = "Activas",
                count = counts[AlertFilter.ACTIVE] ?: 0,
                isSelected = selectedFilter == AlertFilter.ACTIVE,
                onClick = { onFilterSelected(AlertFilter.ACTIVE) },
                modifier = Modifier.weight(1f)
            )
            FilterTabItem(
                label = "Resueltas",
                count = counts[AlertFilter.RESOLVED] ?: 0,
                isSelected = selectedFilter == AlertFilter.RESOLVED,
                onClick = { onFilterSelected(AlertFilter.RESOLVED) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FilterTabItem(
    label: String,
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (isSelected) Color.White else Color.Transparent,
        shape = RoundedCornerShape(20.dp),
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$label ($count)",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.Black else Color.Gray
                )
            )
        }
    }
}
