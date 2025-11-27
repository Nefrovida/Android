package com.example.nefrovida.data.mapper

import com.example.nefrovida.data.remote.dto.Message
import com.example.nefrovida.data.remote.dto.MessageForumInfo
import com.example.nefrovida.domain.model.MessageForumInfoObj
import com.example.nefrovida.domain.model.MessageObj

fun Message.toDomain(): MessageObj =
    MessageObj(
        messageId,
        content,
        likes,
        replies,
        forum =
            MessageForumInfoObj(
                forumId = forum.forumId,
                name = forum.name,
            ),
    )
