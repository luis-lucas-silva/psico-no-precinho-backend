package com.luislucassilva.psiconoprecinho.ports.database.chat

import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import java.util.*

interface ChatRepository {
    suspend fun findByPatient(patientId: UUID): List<Chat>
    suspend fun create(chat: Chat): Chat?
    suspend fun findByPsychologist(psychologistId: UUID): List<Chat>
    suspend fun findById(id: UUID): Chat?
    suspend fun findMessages(chat: Chat): Chat?
}