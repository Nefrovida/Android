package com.example.nefrovida.ui.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nefrovida.R
import com.example.nefrovida.presentation.navigation.NavDestination

@Suppress("ktlint:standard:function-naming")
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
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nefrovidalogo),
                    contentDescription = "Logo",
                    modifier = Modifier.height(32.dp),
                )
            }
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
