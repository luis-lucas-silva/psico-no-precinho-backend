package com.luislucassilva.psiconoprecinho.services

import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import com.luislucassilva.psiconoprecinho.ports.database.chat.ChatRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ChatService(
    private val chatRepository: ChatRepository
) {

    suspend fun findByPatient(id: UUID): List<Chat> {
        return chatRepository.findByPatient(id)
    }

    suspend fun create(chat: Chat): Chat? {
        return chatRepository.create(chat.copy(id= UUID.randomUUID()))
    }

    suspend fun findByPsychologist(id: UUID): List<Chat> {
        return chatRepository.findByPsychologist(id)
    }

    suspend fun findById(id: UUID): Chat? {
        return chatRepository.findById(id)
    }

    suspend fun findMessages(chat: Chat): Chat? {
        return chatRepository.findMessages(chat)
    }
}