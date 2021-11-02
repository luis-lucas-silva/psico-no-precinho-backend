package com.luislucassilva.psiconoprecinho.adapters.r2dbc.chat

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.chat.ChatSqlExpressions.FIND_BY_ID
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.chat.ChatSqlExpressions.FIND_BY_PATIENT
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.chat.ChatSqlExpressions.FIND_BY_PSYCHOLOGIST
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.chat.ChatSqlExpressions.FIND_MESSAGES
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.chat.ChatSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.message.MessageR2bdcRepository
import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import com.luislucassilva.psiconoprecinho.domain.chat.Message
import com.luislucassilva.psiconoprecinho.ports.database.chat.ChatRepository
import com.luislucassilva.psiconoprecinho.utils.bindIfNotNull
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.toList
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@Repository
open class ChatR2bdcRepository (
    private val databaseClient: DatabaseClient,
) : ChatRepository {

    override suspend fun findByPatient(patientId: UUID): List<Chat> {
        val chats = databaseClient.sql(FIND_BY_PATIENT)
            .bind("patient", patientId.toString())
            .map(::rowMapper)
            .flow()
            .toList()

        return chats
    }

    override suspend fun create(chat: Chat): Chat? {

        with(chat) {
            databaseClient.sql(INSERT)
                .bindIfNotNull("id", id.toString())
                .bind("patient", patient.toString())
                .bind("psychologist", psychologist.toString())
                .await()

            val chatInserted = findById(id!!)

            return chatInserted
        }
    }

    override suspend fun findByPsychologist(psychologistId: UUID): List<Chat> {
        val chats =  databaseClient.sql(FIND_BY_PSYCHOLOGIST)
            .bind("psychologist", psychologistId.toString())
            .map(::rowMapper)
            .flow()
            .toList()

        return chats
    }

    override suspend fun findById(id: UUID): Chat? {
        return databaseClient.sql(FIND_BY_ID)
            .bind("id", id.toString())
            .map(::rowMapper)
            .awaitOneOrNull()
    }

    override suspend fun findMessages(chat: Chat): Chat? {
        with(chat) {
            messages = databaseClient.sql(FIND_MESSAGES)
                .bind("id", id.toString())
                .map(::messageRowMapper)
                .flow()
                .toList()


            return chat.copy(messages = messages)

        }

    }

    private fun rowMapper(row: Row): Chat {
        return Chat(
            id = UUID.fromString(row.get("idConversa") as String),
            patient = UUID.fromString(row.get("Paciente_idPaciente") as String),
            psychologist = UUID.fromString(row.get("Psicologo_idPsicologo") as String)
        )
    }

    private fun messageRowMapper(row: Row): Message {
        return Message(
            id = UUID.fromString(row.get("idMensagem") as String),
            content = row.get("Conteudo") as String,
            date = row.get("Data") as LocalDateTime,
            chat = UUID.fromString(row.get("Conversa_idConversa") as String),
            sender = UUID.fromString(row.get("Remetente") as String),
            receiver = UUID.fromString(row.get("Destinatario") as String)
        )
    }
}



