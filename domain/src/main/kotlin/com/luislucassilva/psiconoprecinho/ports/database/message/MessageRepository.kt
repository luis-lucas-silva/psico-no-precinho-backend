package com.luislucassilva.psiconoprecinho.ports.database.message

import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import com.luislucassilva.psiconoprecinho.domain.chat.Message

interface MessageRepository {
    suspend fun create(message: Message): Message?
    suspend fun findByChat(chat: Chat): List<Message>
}