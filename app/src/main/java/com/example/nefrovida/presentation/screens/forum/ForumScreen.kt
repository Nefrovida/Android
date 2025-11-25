package com.example.nefrovida.presentation.screens.forum

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.* 
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.data.remote.dto.MyForumDto
import com.example.nefrovida.presentation.navigation.Screen
import com.example.nefrovida.ui.molecules.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ForumViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading
    var selectedTabIndex by remember { mutableStateOf(0) }
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredMyForums by viewModel.filteredMyForums.collectAsState()

    val tabs = listOf("Mis Foros", "Descubrir")

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Foros") })} // Puedes personalizar este TopAppBar si es necesario
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> {
                    // Mis Foros
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        SearchBar(
                            query = searchQuery,
                            onQueryChange = viewModel::onSearchQueryChange,
                            onSearch = viewModel::onSearch,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(filteredMyForums) { myForum ->
                                    ForumListItem(myForum = myForum) {
                                        navController.navigate(Screen.ForumFeed.createRoute(myForum.forum.forumId))
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Descubrir (Placeholder)
                    Column(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        SearchBar(
                            query = "", // No hay lógica de búsqueda para Descubrir en este momento
                            onQueryChange = { /* No-op */ },
                            onSearch = { /* No-op */ },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Funcionalidad de Descubrir Foros en construcción")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumListItem(
    myForum: MyForumDto,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = myForum.forum.name, style = MaterialTheme.typography.titleMedium)
            // Puedes añadir más detalles del foro aquí si los DTOs lo permiten
        }
    }
}
