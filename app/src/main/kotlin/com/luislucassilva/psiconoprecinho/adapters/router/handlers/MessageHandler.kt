package com.luislucassilva.psiconoprecinho.adapters.router.handlers

import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import com.luislucassilva.psiconoprecinho.domain.chat.Message
import com.luislucassilva.psiconoprecinho.services.MessageService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.util.*

@Component
class MessageHandler(
    private val messageService: MessageService
) {

    suspend fun create(serverRequest: ServerRequest): ServerResponse {
        val message = serverRequest.bodyToMono(Message::class.java).awaitFirst()

        val messageInserted = messageService.create(message)
        return if (messageInserted != null) {
            ServerResponse.status(HttpStatus.CREATED).bodyValueAndAwait(messageInserted)
        } else {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).buildAndAwait()
        }
    }

    suspend fun findByChat(serverRequest: ServerRequest): ServerResponse {
        val chat = serverRequest.bodyToMono(Chat::class.java).awaitFirst()

        val messages = messageService.findByChat(chat)

        return ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(messages)
    }

    suspend fun findById(serverRequest: ServerRequest): ServerResponse {
        val id = UUID.fromString(serverRequest.pathVariable("id"))

        val message = messageService.findById(id)

        return if (message != null) {
            ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(message)
        } else {
            ServerResponse.status(HttpStatus.NOT_FOUND).buildAndAwait()
        }
    }

    suspend fun read(serverRequest: ServerRequest): ServerResponse {
        val id = UUID.fromString(serverRequest.pathVariable("id"))

        val message = messageService.read(id)

        return if (message != null) {
            ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(message)
        } else {
            ServerResponse.status(HttpStatus.NOT_FOUND).buildAndAwait()
        }
    }
}
