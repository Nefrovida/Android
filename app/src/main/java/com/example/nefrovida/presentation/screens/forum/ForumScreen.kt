package com.example.nefrovida.presentation.screens.forum

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nefrovida.data.remote.dto.ForumComplete
import com.example.nefrovida.data.remote.dto.SimpleForumInfo
import com.example.nefrovida.presentation.navigation.Screen
import com.example.nefrovida.ui.molecules.SearchBar
import com.example.nefrovida.ui.organisms.ForumPostCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ForumViewModel = hiltViewModel(),
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Descubrir", "Mis Foros", "Todos los Foros")
    val navyBlue = Color(0xFF000080)

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            title,
                            color =
                                if (selectedTabIndex ==
                                    index
                                ) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    navyBlue
                                },
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = navyBlue,
                )
            }
        }

        when (selectedTabIndex) {
            0 -> DiscoverTabContent(viewModel = viewModel, navController = navController)
            1 -> MyForumsTabContent(viewModel = viewModel, navController = navController)
            2 -> AllForumsTabContent(viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun DiscoverTabContent(
    viewModel: ForumViewModel,
    navController: NavController,
) {
    val discoverFeed by viewModel.discoverFeed.collectAsState()
    val isDiscoverLoading by viewModel.isDiscoverLoading
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        // No hay barra de búsqueda para Descubrir según la documentación
        if (isDiscoverLoading && discoverFeed.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else if (discoverFeed.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Aún no hay mensajes o foros con mensajes", color = Color(0xFF000080))
            }
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            ) {
                items(discoverFeed) { post ->
                    ForumPostCard(
                        post = post,
                        onClick = {
                            navController.navigate(
                                Screen.Message.createRoute(
                                    forumId = post.forum.forumId,
                                    messageId = post.messageId,
                                ),
                            )
                        },
                    )
                }
                if (isDiscoverLoading) {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
                .lastOrNull()
                ?.index
        }.collect { lastVisibleItemIndex ->
            // Cargar antes de llegar al final
            if (lastVisibleItemIndex != null &&
                lastVisibleItemIndex >= listState.layoutInfo.totalItemsCount - 1 - 2
            ) {
                viewModel.loadDiscoverFeed()
            }
        }
    }
}

@Composable
fun MyForumsTabContent(
    viewModel: ForumViewModel,
    navController: NavController,
) {
    val myForumsSearchQuery by viewModel.myForumsSearchQuery.collectAsState()
    val filteredMyForums by viewModel.filteredMyForums.collectAsState()
    val isMyForumsLoading by viewModel.isMyForumsLoading

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = myForumsSearchQuery,
            onQueryChange = viewModel::onMyForumsSearchQueryChange,
            onSearch = viewModel::onSearch,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isMyForumsLoading && filteredMyForums.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else if (filteredMyForums.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Aún no perteneces a ningún foro", color = Color(0xFF000080))
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(filteredMyForums) { simpleForumInfo ->
                    ForumListItem(simpleForumInfo = simpleForumInfo) {
                        navController.navigate(Screen.ForumFeed.createRoute(simpleForumInfo.forumId))
                    }
                }
            }
        }
    }
}

@Composable
fun AllForumsTabContent(
    viewModel: ForumViewModel,
    navController: NavController,
) {
    val allForumsSearchQuery by viewModel.allForumsSearchQuery.collectAsState()
    val filteredAllForums by viewModel.filteredAllForums.collectAsState()
    val isAllForumsLoading by viewModel.isAllForumsLoading
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = allForumsSearchQuery,
            onQueryChange = viewModel::onAllForumsSearchQueryChange,
            onSearch = viewModel::onSearch,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isAllForumsLoading && filteredAllForums.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else if (filteredAllForums.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("Aún no hay foros", color = Color(0xFF000080))
            }
        } else {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            ) {
                items(filteredAllForums) { forumComplete ->
                    ForumAllListItem(forumComplete = forumComplete) {
                        // Aquí puedes decidir a dónde navegar, por ejemplo, al ForumFeedScreen
                        navController.navigate(Screen.ForumFeed.createRoute(forumComplete.forumId))
                    }
                }
                if (isAllForumsLoading) {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo
                .lastOrNull()
                ?.index
        }.collect { lastVisibleItemIndex ->
            // Cargar antes de llegar al final
            if (lastVisibleItemIndex != null &&
                lastVisibleItemIndex >= listState.layoutInfo.totalItemsCount - 1 - 2
            ) {
                viewModel.loadAllForums()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumListItem(
    simpleForumInfo: SimpleForumInfo,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = simpleForumInfo.name,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumAllListItem(
    forumComplete: ForumComplete,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = forumComplete.name, style = MaterialTheme.typography.titleMedium)
            Text(text = forumComplete.description, style = MaterialTheme.typography.bodySmall)
            // Puedes añadir más detalles aquí si lo deseas, como el creador.
        }
    }
}
