package com.luislucassilva.psiconoprecinho.adapters.router.handlers

import com.luislucassilva.psiconoprecinho.domain.login.LoginRequest
import com.luislucassilva.psiconoprecinho.services.AdministratorService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.util.*

@Component
class AdministratorHandler(
    private val administratorService: AdministratorService
) {

    suspend fun findByUserNameAndPasswordRequest(serverRequest: ServerRequest): ServerResponse {
        val patientLoginRequest = serverRequest.awaitBodyOrNull<LoginRequest>()

        patientLoginRequest?.let{
            val patient = administratorService.findByUserNameAndPassword(
                it.userName!!,
                it.password!!
            )

            return when{
                !it.isValid() -> ServerResponse.badRequest().buildAndAwait()
                patient != null -> ServerResponse.ok().bodyValueAndAwait(patient)
                else -> ServerResponse.status(HttpStatus.UNAUTHORIZED).buildAndAwait()
            }

        } ?:return ServerResponse.badRequest().buildAndAwait()
    }

    suspend fun create(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun updateById(serverRequest: ServerRequest): ServerResponse {
        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun findById(serverRequest: ServerRequest): ServerResponse {
        val id = UUID.fromString(serverRequest.pathVariable("id"))

        val administrator = administratorService.findById(id)

        return if (administrator != null) {
            ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(administrator)
        }
        else {
            ServerResponse.status(HttpStatus.NOT_FOUND).buildAndAwait()
        }
    }
}