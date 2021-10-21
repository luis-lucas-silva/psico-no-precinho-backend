package com.luislucassilva.psiconoprecinho.adapters.router.handlers

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class AdministratorHandler {

    suspend fun findByUserNameAndPasswordRequest(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun create(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun updateById(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun findById(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok().buildAndAwait()
    }
}