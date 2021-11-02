package com.luislucassilva.psiconoprecinho.ports.database.message

import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import com.luislucassilva.psiconoprecinho.domain.chat.Message
import java.util.*

interface MessageRepository {
    suspend fun create(message: Message): Message?
    suspend fun findByChat(chat: Chat): List<Message>
    suspend fun findById(id: UUID): Message?
}