package com.example.sebwave.features.users.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sebwave.features.users.presentation.components.SebWaveButton
import com.example.sebwave.features.users.presentation.components.SebWaveTextField
import com.example.sebwave.features.users.presentation.viewmodel.AuthViewModel
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()
    val username by viewModel.username.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    var showErrorDialog by remember { mutableStateOf(false) }

    if (uiState.errorMessage != null && showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Error de registro") },
            text = { Text(uiState.errorMessage ?: "Error desconocido") }
        )
    }

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            navController.navigate("login") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SebWave",
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Crea tu cuenta",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                    
                    Text(
                        text = "Regístrate para comenzar a gestionar tus dispositivos",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SebWaveTextField(
                        value = username,
                        onValueChange = { viewModel.onUsernameChange(it) },
                        label = "Nombre de Usuario",
                        placeholder = "Tu usuario"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SebWaveTextField(
                        value = email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        label = "Correo Electrónico",
                        placeholder = "ejemplo@sebwave.com"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SebWaveTextField(
                        value = password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        label = "Contraseña",
                        placeholder = "••••••••",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    SebWaveButton(
                        text = "Registrarse",
                        onClick = {
                            viewModel.register()
                            showErrorDialog = true
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(onClick = { navController.navigate("login") }) {
                        Text("¿Ya tienes cuenta? Inicia sesión")
                    }
                }
            }
        }
    }
}