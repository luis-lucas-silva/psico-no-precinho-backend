package com.luislucassilva.psiconoprecinho.services

import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import com.luislucassilva.psiconoprecinho.domain.chat.Message
import com.luislucassilva.psiconoprecinho.ports.database.message.MessageRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MessageService(
    private val messageRepository: MessageRepository
) {

    suspend fun create(message: Message): Message? {
        return messageRepository.create(message.copy(id= UUID.randomUUID()))
    }

    suspend fun findByChat(chat: Chat): List<Message> {
        return messageRepository.findByChat(chat)
    }
}