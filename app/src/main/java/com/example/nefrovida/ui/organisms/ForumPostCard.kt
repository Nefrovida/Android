package com.example.nefrovida.ui.organisms

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nefrovida.data.remote.dto.ForumMessageDto

@Composable
fun ForumPostCard(
    modifier: Modifier = Modifier,
    post: ForumMessageDto
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.forums.name, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Likes")
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
