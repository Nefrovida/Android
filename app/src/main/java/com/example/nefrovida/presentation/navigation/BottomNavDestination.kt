package com.example.nefrovida.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavDestination (
    val route: String,
    val label: String,
    val icon: ImageVector,
    val filledIcon: ImageVector
){
    object Home : BottomNavDestination(
        route = "home",
        label = "Inicio",
        icon = Icons.Outlined.Home,
        filledIcon = Icons.Filled.Home)
    object Lab : BottomNavDestination(
        route = "labs",
        label = "Análisis",
        icon = Icons.Outlined.Science,
        filledIcon = Icons.Filled.Science)
    object Forum : BottomNavDestination(
        route = "forum",
        label = "Foro",
        icon = Icons.Outlined.Forum,
        filledIcon = Icons.Filled.Forum)
    object Agenda : BottomNavDestination(
        route = "agenda",
        label = "Agenda",
        icon = Icons.Outlined.CalendarMonth,
        filledIcon = Icons.Filled.CalendarMonth)
    companion object {
        val items = listOf(Home, Lab, Forum, Agenda)
    }
}
