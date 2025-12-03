package com.example.nefrovida.ui.organisms

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
    Card(
        modifier =
            modifier
                .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors =
            CardDefaults.cardColors(
                containerColor = Color.White,
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
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier =
                        Modifier.clickable(
                            onClick = { viewModel.postLike(post.messageId) },
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Likes",
                    )
                    Text(text = "${post.likes}", modifier = Modifier.padding(start = 4.dp))
                }
                Row {
                    Icon(imageVector = Icons.Default.Reply, contentDescription = "Replies")
                    Text(text = "${post.replies}", modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
    }
}
