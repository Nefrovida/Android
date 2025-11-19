package com.example.nefrovida.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavDestination (
    val route: String,
    val label: String,
    val icon: ImageVector,
    val filledIcon: ImageVector? = null
){
    object Home : NavDestination(
        route = "home",
        label = "Inicio",
        icon = Icons.Outlined.Home,
        filledIcon = Icons.Filled.Home)
    object Lab : NavDestination(
        route = "labs",
        label = "Análisis",
        icon = Icons.Outlined.Science,
        filledIcon = Icons.Filled.Science)
    object Forum : NavDestination(
        route = "forum",
        label = "Foro",
        icon = Icons.Outlined.Forum,
        filledIcon = Icons.Filled.Forum)
    object Agenda : NavDestination(
        route = "agenda",
        label = "Agenda",
        icon = Icons.Outlined.CalendarMonth,
        filledIcon = Icons.Filled.CalendarMonth)

    object Notifications : NavDestination(
        route = "notifications",
        label = "Notificaciones",
        icon = Icons.Outlined.Notifications
    )
    companion object {
        val bottomNavItems = listOf(Home, Lab, Forum, Agenda)
        val topNavItems = listOf(Notifications)
    }
}
