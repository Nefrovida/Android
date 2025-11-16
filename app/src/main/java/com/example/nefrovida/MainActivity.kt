package com.example.nefrovida

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nefrovida.presentation.screens.login.LoginScreen
import com.example.nefrovida.ui.theme.NefrovidaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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


