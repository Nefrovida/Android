package com.example.nefrovida.presentation.screens.forum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

data class ParentMessageInfo(
    val forumId: Int,
    val messageId: Int,
    val page: Int = 0,
    val limit: Int = 10,
)

@Composable
fun ForumMessageScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    pMI: ParentMessageInfo,
    viewModel: ForumMessageViewModel = hiltViewModel(),
) {
    val messageReplyUiState by viewModel.messageReplies.collectAsStateWithLifecycle()

    LaunchedEffect(pMI) {
        viewModel.loadReplies(
            pMI.forumId,
            pMI.messageId,
            pMI.page,
            pMI.limit,
        )
    }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
    }
}
