package com.example.nefrovida.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nefrovida.presentation.screens.NotificationsScreen
import com.example.nefrovida.presentation.screens.agenda.AgendaScreen
import com.example.nefrovida.presentation.screens.catalog.CatalogScreen
import com.example.nefrovida.presentation.screens.forum.ForumFeedScreen
import com.example.nefrovida.presentation.screens.forum.ForumScreen
import com.example.nefrovida.presentation.screens.home.HomeScreen
import com.example.nefrovida.presentation.screens.laboratory.AnalysisDetailScreen
import com.example.nefrovida.presentation.screens.laboratory.AnalysisHistoryScreen
import com.example.nefrovida.presentation.screens.laboratory.LaboratoryScreen
import com.example.nefrovida.presentation.screens.laboratory.ReportDetailScreen
import com.example.nefrovida.presentation.screens.login.LoginScreen

sealed class Screen(
    val route: String,
) {
    object Login : Screen("login")

    object Home : Screen("home")

    object Catalog : Screen("catalog")

    object Laboratory : Screen("labs")

    object AnalysisHistory : Screen("labs/history")

    object AnalysisDetail : Screen("labs/details/{analysisId}") {
        fun createRoute(analysisId: Int) = "labs/details/$analysisId"
    }

    object ReportDetail : Screen("reportDetail/{patientAnalysisId}") {
        fun createRoute(id: Int) = "reportDetail/$id"
    }

    object Agenda : Screen("agenda")

    object Forum : Screen("forum")

    object ForumFeed : Screen("forumFeed/{forumId}") {
        fun createRoute(forumId: Int) = "forumFeed/$forumId"
    }

    object Notifications : Screen("notifications")
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun NefrovidaNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier,
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    // TODO: Navegar a pantalla de registro
                },
                onNavigateToForgotPassword = {
                    // TODO: Navegar a pantalla de recuperación de contraseña
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Catalog.route) {
            CatalogScreen(
                Modifier = Modifier,
                navController = navController,
            )
        }

        composable(route = Screen.Laboratory.route) {
            LaboratoryScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
            )
        }
        composable(route = Screen.Forum.route) {
            ForumScreen(
                navController = navController,
            )
        }
        composable(
            route = Screen.ForumFeed.route,
            arguments = listOf(navArgument("forumId") { type = NavType.IntType }),
        ) {
            ForumFeedScreen(
                navController = navController,
            )
        }
        composable(route = Screen.Agenda.route) {
            AgendaScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
            )
        }
        composable(route = Screen.Laboratory.route) {
            AnalysisHistoryScreen(navController = navController)
        }
        composable(
            route = Screen.AnalysisDetail.route,
            arguments = listOf(navArgument("analysisId") { type = NavType.IntType }),
        ) { backStackEntry ->
            val analysisId = backStackEntry.arguments?.getInt("analysisId") ?: 0
            AnalysisDetailScreen(
                analysisId = analysisId,
                navController = navController,
            )
        }

        composable(
            route = Screen.ReportDetail.route,
            arguments =
                listOf(
                    navArgument("patientAnalysisId") { type = NavType.IntType },
                ),
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("patientAnalysisId") ?: 0

            ReportDetailScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                patientAnalysisId = id,
            )
        }
        composable(route = Screen.Notifications.route) {
            NotificationsScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
