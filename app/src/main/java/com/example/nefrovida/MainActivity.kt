package com.example.nefrovida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nefrovida.presentation.screens.add_analysis.AddAnalysisScreen
import com.example.nefrovida.presentation.screens.analysis.AnalysisListScreen
import com.example.nefrovida.ui.theme.NefrovidaTheme
import dagger.hilt.android.AndroidEntryPoint

// --- Navigation Routes ---
data class Screen(val route: String, val label: String, val icon: ImageVector, val iconSelected: ImageVector)

val navItems = listOf(
    Screen("home", "Inicio", Icons.Outlined.Home, Icons.Filled.Home),
    Screen("analysis", "Análisis", Icons.Outlined.List, Icons.Filled.List),
    Screen("forums", "Foros", Icons.Outlined.MailOutline, Icons.Filled.MailOutline),
    Screen("agenda", "Agenda", Icons.Outlined.DateRange, Icons.Filled.DateRange),
)

const val ADD_ANALYSIS_ROUTE = "add_analysis"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NefrovidaTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = { AppTopBar() },
                    bottomBar = {
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            navItems.forEach { screen ->
                                val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                                NavigationBarItem(
                                    icon = { Icon(if (isSelected) screen.iconSelected else screen.icon, contentDescription = null) },
                                    label = { Text(screen.label) },
                                    selected = isSelected,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "analysis",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Main screens from Bottom Nav
                        composable("home") { /* Placeholder for Home Screen */ Text("Home Screen") }
                        composable("analysis") { 
                            AnalysisListScreen(onNavigateToAdd = { navController.navigate(ADD_ANALYSIS_ROUTE) })
                        }
                        composable("forums") { /* Placeholder for Forums Screen */ Text("Forums Screen") }
                        composable("agenda") { /* Placeholder for Agenda Screen */ Text("Agenda Screen") }

                        // Secondary screens
                        composable(ADD_ANALYSIS_ROUTE) {
                            AddAnalysisScreen(
                                onAnalysisAdded = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBar() {
    TopAppBar(
        title = { /* Logo Placeholder */ Text("NEFRO Vida") },
        actions = {
            IconButton(onClick = { /* TODO: Navigate to Profile */ }) {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Profile")
            }
            IconButton(onClick = { /* TODO: Navigate to Notifications */ }) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
            }
        }
    )
}
