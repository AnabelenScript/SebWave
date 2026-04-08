package com.example.sebwave.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sebwave.features.dashboard.presentation.screens.DashboardScreen
import com.example.sebwave.features.users.presentation.screens.LoginScreen
import com.example.sebwave.features.users.presentation.screens.RegisterScreen

@Composable
fun NavigationWrapper(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable("dashboard") {
            DashboardScreen()
        }
    }
}
