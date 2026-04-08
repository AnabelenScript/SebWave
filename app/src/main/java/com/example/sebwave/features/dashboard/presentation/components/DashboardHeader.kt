package com.example.sebwave.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SebWave",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                ),
                color = Color.Black
            )
            // Logo colors indicator
            Row(modifier = Modifier.padding(start = 4.dp)) {
                Box(modifier = Modifier.size(12.dp, 4.dp).background(Color.Red))
                Box(modifier = Modifier.size(12.dp, 4.dp).background(Color.Yellow))
                Box(modifier = Modifier.size(12.dp, 4.dp).background(Color.Green))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Panel de control",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
    }
}
