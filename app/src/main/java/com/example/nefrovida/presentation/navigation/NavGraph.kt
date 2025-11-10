package com.example.nefrovida.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nefrovida.presentation.screens.agenda.AgendaScreen
import com.example.nefrovida.presentation.screens.home.HomeScreen
import com.example.nefrovida.presentation.screens.laboratory.LaboratoryScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Laboratory : Screen("labs")
    object Agenda : Screen("agenda")
}
@Suppress("ktlint:standard:function-naming")
@Composable
fun NefrovidaNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable( route = Screen.Laboratory.route) {
            LaboratoryScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() })
        }
        composable(route = Screen.Agenda.route) {
            AgendaScreen( navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
