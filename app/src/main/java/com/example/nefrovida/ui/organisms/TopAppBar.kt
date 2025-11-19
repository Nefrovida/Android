package com.example.nefrovida.ui.organisms

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import com.example.nefrovida.presentation.navigation.NavDestination
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NfTopAppBar(
    navController: NavController,
    onProfileClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                    contentDescription = "Perfil"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(NavDestination.Notifications.route) {
                        launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    imageVector = NavDestination.Notifications.icon,
                    contentDescription = NavDestination.Notifications.label
                )
            }
        }
    )
}
