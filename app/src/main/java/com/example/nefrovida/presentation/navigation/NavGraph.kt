package com.example.nefrovida.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nefrovida.presentation.screens.agenda.AgendaScreen
import com.example.nefrovida.presentation.screens.forum.ForumScreen
import com.example.nefrovida.presentation.screens.home.HomeScreen
import com.example.nefrovida.presentation.screens.laboratory.LaboratoryScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.nefrovida.presentation.screens.agenda.AppointmentDetailScreen
import com.example.nefrovida.presentation.screens.laboratory.ReportDetailScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Laboratory : Screen("labs")
    object ReportDetail : Screen("reportDetail/{patientAnalysisId}"){
        fun createRoute(id: Int) = "reportDetail/$id"
    }
    object Agenda : Screen("agenda")
    object Forum : Screen ("forum")
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
        composable(
            route = Screen.ReportDetail.route,
            arguments = listOf(
                navArgument("patientAnalysisId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("patientAnalysisId") ?: 0
            ReportDetailScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                patientAnalysisId = id
            )
        }
        composable( route = Screen.Forum.route) {
            ForumScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() })
        }
        composable(route = Screen.Agenda.route) {
            AgendaScreen( navController = navController,
                onBackClick = { navController.popBackStack() }
            )
        }
        // Inside NavHost en NavGraph.kt
        composable(
            route = "appointment_detail/{appointmentId}",
            arguments = listOf(navArgument("appointmentId") { type = NavType.StringType })
        ) { backStackEntry ->
            val appointmentId = backStackEntry.arguments?.getString("appointmentId")
            if (appointmentId != null) {
                AppointmentDetailScreen(
                    appointmentId = appointmentId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
