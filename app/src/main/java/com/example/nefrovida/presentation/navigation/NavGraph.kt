package com.example.nefrovida.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
// Importamos las pantallas que SÍ existen
import com.example.nefrovida.presentation.screens.agenda.AppointmentDetailScreen
import com.example.nefrovida.presentation.screens.forum.ForumScreen
import com.example.nefrovida.presentation.screens.home.HomeScreen
import com.example.nefrovida.presentation.screens.laboratory.LaboratoryScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Laboratory : Screen("labs")
    object Agenda : Screen("agenda")
    object Forum : Screen("forum")
    object AppointmentDetail : Screen("appointment_detail")
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

        // --- RUTA DEL PACIENTE (TU FEATURE) ---
        composable(route = Screen.Laboratory.route) {
            LaboratoryScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() })
        }

        // --- RUTA DE DETALLES (TU FEATURE) ---
        composable(
            route = Screen.AppointmentDetail.route + "/{appointmentId}",
            arguments = listOf(
                navArgument("appointmentId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            // Extraemos el ID
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            // Llamamos al Composable CORRECTO
            AppointmentDetailScreen(
                appointmentId = appointmentId,
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- OTRAS RUTAS (DEJAREMOS EL FORO) ---
        composable(route = Screen.Forum.route) {
            ForumScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- RUTA ROTA (SECRETARIA) - LA APUNTAMOS AL FORO POR AHORA ---
        // Esto arregla el error 'Unresolved reference AgendaScreen'
        composable(route = Screen.Agenda.route) {
            ForumScreen( // Apuntamos temporalmente al foro
                navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}