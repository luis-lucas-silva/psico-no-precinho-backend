package com.luislucassilva.psiconoprecinho.adapters.router.handlers

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class HomeHandler {

    suspend fun getHome(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyValueAndAwait("Ola, esse e o backend")
    }
}