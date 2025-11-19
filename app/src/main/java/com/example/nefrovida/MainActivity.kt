package com.example.nefrovida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nefrovida.presentation.screens.login.LoginScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nefrovida.ui.theme.NefrovidaTheme
import com.example.nefrovida.presentation.navigation.NefrovidaNavGraph
import com.example.nefrovida.ui.organisms.NfBottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NefrovidaTheme {
                LoginScreen(
                    onNavigateToRegister = {
                        // TODO: Navegar a pantalla de registro
                    },
                    onNavigateToForgotPassword = {
                        // TODO: Navegar a pantalla de recuperación de contraseña
                    },
                    onLoginSuccess = {
                        // TODO: Navegar a pantalla principal
                    }
                )
            }
        }
    }
}


