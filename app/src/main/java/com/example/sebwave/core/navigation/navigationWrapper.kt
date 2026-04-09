package com.example.sebwave.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sebwave.core.ui.components.SebWaveNavigationBar
import com.example.sebwave.features.alerts.presentation.screens.AlertScreen
import com.example.sebwave.features.dashboard.presentation.screens.DashboardScreen
import com.example.sebwave.features.map.presentation.screens.MapScreen
import com.example.sebwave.features.users.presentation.screens.LoginScreen
import com.example.sebwave.features.users.presentation.screens.RegisterScreen

@Composable
fun NavigationWrapper(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute != "login" && currentRoute != "register"

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                SebWaveNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = modifier.padding(innerPadding)
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
            composable("map") {
                MapScreen()
            }
            composable("alerts") {
                AlertScreen()
            }
        }
    }
}
