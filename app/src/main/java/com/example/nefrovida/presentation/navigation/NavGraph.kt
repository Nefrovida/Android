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
import com.example.nefrovida.presentation.screens.laboratory.AnalysisDetailScreen
import com.example.nefrovida.presentation.screens.laboratory.AnalysisHistoryScreen
import com.example.nefrovida.presentation.screens.laboratory.LaboratoryScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Laboratory : Screen("labs")
    object AnalysisHistory : Screen("labs/history")
    object AnalysisDetail : Screen("labs/details/{analysisId}") {
        fun createRoute(analysisId: Int) = "labs/details/$analysisId"
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
            AnalysisHistoryScreen(
                navController = navController)
        }
        composable(
            route = Screen.AnalysisDetail.route,
            arguments = listOf(navArgument("analysisId") { type = NavType.IntType })
        ) { backStackEntry ->
            val analysisId = backStackEntry.arguments?.getInt("analysisId") ?: 0
            AnalysisDetailScreen(
                analysisId = analysisId,
                navController = navController)
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
    }
}
