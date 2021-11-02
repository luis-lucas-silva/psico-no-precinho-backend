package com.luislucassilva.psiconoprecinho.adapters.r2dbc.message

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.message.MessageSqlExpressions.FIND_BY_CHAT
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.message.MessageSqlExpressions.FIND_BY_ID
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.message.MessageSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.psychologist.PsychologistSqlExpressions
import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import com.luislucassilva.psiconoprecinho.domain.chat.Message
import com.luislucassilva.psiconoprecinho.ports.database.chat.ChatRepository
import com.luislucassilva.psiconoprecinho.ports.database.message.MessageRepository
import com.luislucassilva.psiconoprecinho.utils.bindIfNotNull
import com.luislucassilva.psiconoprecinho.utils.get
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
open class MessageR2bdcRepository (
    private val databaseClient: DatabaseClient,
) : MessageRepository {
    override suspend fun create(message: Message): Message? {
        with(message) {
            databaseClient.sql(INSERT)
                .bindIfNotNull("id", id.toString())
                .bind("content", content)
                .bind("date", date)
                .bind("chat", chat.toString())
                .bind("sender", sender.toString())
                .bind("receiver", receiver.toString())
                .await()

            val messageInserted = findById(id!!)

            return messageInserted
        }

    }

    override suspend fun findByChat(chat: Chat): List<Message> {
        return databaseClient.sql(FIND_BY_CHAT)
            .bind("id", chat.id.toString())
            .map(::rowMapper)
            .flow()
            .toList()
    }

    override suspend fun findById(id: UUID): Message? {
        return databaseClient.sql(FIND_BY_ID)
            .bind("id", id.toString())
            .map(::rowMapper)
            .awaitOneOrNull()
    }


    private fun rowMapper(row: Row): Message {
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