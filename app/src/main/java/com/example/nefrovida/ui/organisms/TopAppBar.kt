package com.example.nefrovida.ui.organisms

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.nefrovida.presentation.navigation.NavDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NfTopAppBar(
    navController: NavController,
    onProfileClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    TopAppBar(
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        title = {
            // TODO: ADD nefrovida logo
        },
        navigationIcon = {
            IconButton(onClick = {
                onProfileClick()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Perfil",
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(NavDestination.Notifications.route) {
                        launchSingleTop = true
                    }
                },
            ) {
                Icon(
                    imageVector = NavDestination.Notifications.icon,
                    contentDescription = NavDestination.Notifications.label,
                )
            }
            IconButton(
                onClick = {
                    onLogoutClick()
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Logout,
                    contentDescription = "Cerrar sesión",
                )
            }
        },
    )
}
