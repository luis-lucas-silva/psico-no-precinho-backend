package com.luislucassilva.psiconoprecinho.adapters.router.handlers

import com.luislucassilva.psiconoprecinho.domain.login.LoginRequest
import com.luislucassilva.psiconoprecinho.domain.patient.Patient
import com.luislucassilva.psiconoprecinho.services.PatientService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class PatientHandler(
    private val patientService: PatientService
) {
    suspend fun findByUserNameAndPasswordRequest(serverRequest: ServerRequest): ServerResponse{

        val patientLoginRequest = serverRequest.awaitBodyOrNull<LoginRequest>()

        patientLoginRequest?.let{
            val patient = patientService.findByUserNameAndPassword(
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

    suspend fun create(serverRequest: ServerRequest): ServerResponse{
        val patient = serverRequest.bodyToMono(Patient::class.java).awaitFirst()

        val insertedPatient = patientService.create(patient)
        return if (insertedPatient != null) {
            ServerResponse.status(HttpStatus.CREATED).bodyValueAndAwait(insertedPatient)
        } else {
            ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).buildAndAwait()
        }
    }
}