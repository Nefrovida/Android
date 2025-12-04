package com.example.nefrovida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nefrovida.di.NetworkModule
import com.example.nefrovida.presentation.navigation.NefrovidaNavGraph
import com.example.nefrovida.presentation.navigation.Screen
import com.example.nefrovida.ui.DrawerContent
import com.example.nefrovida.ui.organisms.NfBottomNavigationBar
import com.example.nefrovida.ui.organisms.NfTopAppBar
import com.example.nefrovida.ui.theme.NefrovidaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NefrovidaTheme {
                val navController = rememberNavController()
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStackEntry?.destination?.route

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                val showBottomBar = currentRoute != Screen.Login.route && currentRoute != Screen.Register.route

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerContainerColor = MaterialTheme.colorScheme.surface,
                            drawerContentColor = MaterialTheme.colorScheme.onSurface,
                        ) {
                            DrawerContent { selected ->
                                scope.launch { drawerState.close() }
                            }
                        }
                    },
                ) {
                    Scaffold(
                        topBar = {
                            if (showBottomBar) {
                                NfTopAppBar(
                                    navController = navController,
                                    onProfileClick = {
                                        navController.navigate(Screen.Profile.route) {
                                            launchSingleTop = true
                                        }
                                    },
                                    onLogoutClick = {
                                        // CLEAR COOKIES
                                        NetworkModule.clearCookies()

                                        // NAVIGATE TO LOGIN AND CLEAR HISTORY
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(0)
                                            launchSingleTop = true
                                        }
                                    },
                                )
                            }
                        },
                        bottomBar = {
                            if (showBottomBar) {
                                NfBottomNavigationBar(navController)
                            }
                        },
                    ) { innerPadding ->
                        NefrovidaNavGraph(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding),
                        )
                    }
                }
            }
        }
    }
}
