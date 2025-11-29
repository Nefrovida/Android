package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.MessageForumInfo
import com.example.nefrovida.data.remote.dto.Reply

fun Reply.toDomain(): Message =
    Message(
        messageId = messageId,
        content = content,
        likes = stats.likes,
        replies = stats.likes,
        forum =
            MessageForumInfo(
                forumId,
                "",
            ),
    )
