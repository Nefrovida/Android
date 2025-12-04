package com.example.nefrovida.ui.organisms

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.presentation.screens.forum.ForumPostCardViewModel

@Composable
fun ForumPostCard(
    modifier: Modifier = Modifier,
    post: Message,
    onClick: () -> Unit,
    viewModel: ForumPostCardViewModel = hiltViewModel(),
) {
    val liked = rememberSaveable(post.messageId) { mutableIntStateOf(post.liked) }

    LaunchedEffect(post) {
        liked.intValue = liked.intValue
    }

    Card(
        modifier =
            modifier
                .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        onClick = onClick,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp),
        ) {
            post.forum?.name?.takeIf { it.isNotBlank() }?.let { name ->
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier =
                        Modifier.clickable(
                            onClick = {
                                viewModel.postLike(post.messageId)
                                liked.intValue = if (liked.intValue == 0) 1 else 0
                            },
                        ),
                     verticalAlignment = Alignment.CenterVertically
                ) {
                    if (liked.intValue == 1) {
                        Icon(Icons.Default.Favorite, contentDescription = "Unlike")
                    } else {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Like")
                    }
                    Text(text = "${post.likes + liked.intValue}", modifier = Modifier.padding(start = 4.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Comment,
                        contentDescription = "Replies",
                    )
                    Text(
                        text = "${post.replies}",
                        modifier = Modifier.padding(start = 4.dp),
                    )
                }
            }
        }
    }
}
