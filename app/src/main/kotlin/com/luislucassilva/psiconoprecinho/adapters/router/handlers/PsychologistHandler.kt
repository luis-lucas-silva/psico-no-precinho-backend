package com.luislucassilva.psiconoprecinho.adapters.router.handlers

import com.luislucassilva.psiconoprecinho.domain.login.LoginRequest
import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist
import com.luislucassilva.psiconoprecinho.services.PsychologistService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import java.util.*
import java.util.regex.Pattern

@Component
class PsychologistHandler(
    private val psychologistService: PsychologistService
) {

    suspend fun findByUserNameAndPasswordRequest(serverRequest: ServerRequest): ServerResponse {

        val psychologistLoginRequest = serverRequest.awaitBodyOrNull<LoginRequest>()

        psychologistLoginRequest?.let {
            val psychologist = psychologistService.findByUserNameAndPassword(
                it.userName!!,
                it.password!!
            )

            return when {
                !it.isValid() -> ServerResponse.badRequest().buildAndAwait()
                psychologist != null -> ServerResponse.ok().bodyValueAndAwait(psychologist)
                else -> ServerResponse.status(HttpStatus.UNAUTHORIZED).buildAndAwait()
            }

        } ?: return ServerResponse.badRequest().buildAndAwait()
    }

    suspend fun create(serverRequest: ServerRequest): ServerResponse {
        val psychologist = serverRequest.bodyToMono(Psychologist::class.java).awaitFirst()

        val insertedPsychologist = psychologistService.create(psychologist)
        return if (insertedPsychologist != null) {
            ServerResponse.status(HttpStatus.CREATED).bodyValueAndAwait(insertedPsychologist)
        } else {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).buildAndAwait()
        }

    }

    suspend fun findById(serverRequest: ServerRequest): ServerResponse {
        val id = UUID.fromString(serverRequest.pathVariable("id"))

        val psychologist = psychologistService.findById(id)

        return if (psychologist != null) {
            ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(psychologist)
        }
        else {
            ServerResponse.status(HttpStatus.NOT_FOUND).buildAndAwait()
        }
    }
}