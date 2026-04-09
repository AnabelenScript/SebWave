package com.example.sebwave.features.alerts.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sebwave.R
import com.example.sebwave.core.ui.theme.primaryLight
import com.example.sebwave.core.ui.theme.primaryContainerLight

@Composable
fun AlertsHeaderSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo idéntico al Dashboard HeaderSection
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "SEBWAV",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Black,
                        fontStyle = FontStyle.Italic,
                        letterSpacing = (-2).sp
                    ),
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(36.dp)
                        .offset(y = (-1).dp)
                        .offset(x = (-7).dp)
                )
            }

            // Badge "En línea"
            Surface(
                color = primaryContainerLight,
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "En línea",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    color = primaryLight,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Alertas",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            color = Color.Black
        )
    }
}
