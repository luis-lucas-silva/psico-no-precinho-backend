package com.luislucassilva.psiconoprecinho.adapters.router.handlers

import com.luislucassilva.psiconoprecinho.domain.chat.Chat
import com.luislucassilva.psiconoprecinho.services.ChatService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.util.*

@Component
class ChatHandler(
    private val chatService: ChatService
) {

    suspend fun findByPatient(serverRequest: ServerRequest): ServerResponse {
        val id = UUID.fromString(serverRequest.pathVariable("id"))

        val chats = chatService.findByPatient(id)

        return ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(chats)
    }

    suspend fun create(serverRequest: ServerRequest):ServerResponse {
        val chat = serverRequest.bodyToMono(Chat::class.java).awaitFirst()

        val chatInserted = chatService.create(chat)

        return if (chatInserted!= null) {
            ServerResponse.status(HttpStatus.CREATED).bodyValueAndAwait(chatInserted)
        } else {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).buildAndAwait()
        }
    }

    suspend fun findByPsychologist(serverRequest: ServerRequest): ServerResponse {
        val id = UUID.fromString(serverRequest.pathVariable("id"))

        val chats = chatService.findByPsychologist(id)

        return ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(chats)
    }

    suspend fun findById(serverRequest: ServerRequest): ServerResponse {
        val id = UUID.fromString(serverRequest.pathVariable("id"))

        val chat = chatService.findById(id)

        return if (chat != null) {
            ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(chat)
        }
        else {
            ServerResponse.status(HttpStatus.NOT_FOUND).buildAndAwait()
        }
    }

    suspend fun findMessages(serverRequest: ServerRequest): ServerResponse {
        val chat = serverRequest.bodyToMono(Chat::class.java).awaitFirst()

        val chatUpdated = chatService.findMessages(chat)

        return if (chatUpdated != null) {
            ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(chatUpdated)
        } else {
            ServerResponse.status(HttpStatus.NOT_FOUND).buildAndAwait()
        }
    }
}