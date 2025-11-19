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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.nefrovida.ui.theme.NefrovidaTheme
import com.example.nefrovida.presentation.navigation.NefrovidaNavGraph
import com.example.nefrovida.ui.DrawerContent
import com.example.nefrovida.ui.organisms.NfBottomNavigationBar
import com.example.nefrovida.ui.organisms.NfTopAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NefrovidaTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerContainerColor = MaterialTheme.colorScheme.surface,
                            drawerContentColor = MaterialTheme.colorScheme.onSurface
                        ) {
                            DrawerContent { selected ->
                                scope.launch { drawerState.close() }
                            }
                        }
                    }
                ) {
                    Scaffold(
                        topBar = {
                            NfTopAppBar(
                                navController = navController,
                                onProfileClick = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        },
                        bottomBar = { NfBottomNavigationBar(navController) }
                    ) { innerPadding ->
                        NefrovidaNavGraph(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }

            }
        }
    }
}