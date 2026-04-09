package com.example.sebwave.features.dashboard.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sebwave.R
import com.example.sebwave.core.ui.theme.*

@Composable
fun IntersectionsHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Intersecciones",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold)
        )
        OutlinedButton(
            onClick = { /* TODO */ },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFF4CAF50)),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF4CAF50))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Nuevo semáforo", color = Color(0xFF4CAF50), fontSize = 14.sp)
        }
    }
}
