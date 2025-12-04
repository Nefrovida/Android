package com.example.nefrovida.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun DrawerContent(onItemSelected: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            "Mi Perfil",
            style =
                MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(16.dp))

        NavigationDrawerItem(
            label = { Text("🚧 En construcción 🚧") },
            selected = false,
            onClick = { onItemSelected("home") },
        )
    }
}
