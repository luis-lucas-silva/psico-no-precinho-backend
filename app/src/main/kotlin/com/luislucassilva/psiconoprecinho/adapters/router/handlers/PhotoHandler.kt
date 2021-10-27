package com.luislucassilva.psiconoprecinho.adapters.router.handlers

import com.luislucassilva.psiconoprecinho.domain.photo.PhotoRequest
import com.luislucassilva.psiconoprecinho.domain.photo.PhotoRequestUserType
import com.luislucassilva.psiconoprecinho.domain.photo.PhotoRequestUserType.PSYCHOLOGIST
import com.luislucassilva.psiconoprecinho.services.PatientService
import com.luislucassilva.psiconoprecinho.services.PsychologistService
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpStatus
import org.springframework.http.codec.multipart.FilePart
import org.springframework.http.codec.multipart.FormFieldPart
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Flux
import java.util.*

@Component
class PhotoHandler(
    private val psychologistService: PsychologistService,
    private val patientService: PatientService
) {

    suspend fun createOrUpdate(serverRequest: ServerRequest): ServerResponse {
        val body = serverRequest.awaitMultipartData()
        val photoMultiPart = body["photo"]?.first() as FilePart
        val userMultiPart = body["userType"]?.first() as FormFieldPart

        val userId = UUID.fromString(serverRequest.pathVariable("id"))
        val userType = getUserType(userMultiPart.content())
        val photo = getPhoto(photoMultiPart.content())

        return try {
            val photoRequest = PhotoRequest(userId, PhotoRequestUserType.valueOf(userType!!), photo!!)

            if (photoRequest.userType == PSYCHOLOGIST) {
                psychologistService.createPhoto(photoRequest)
            }
            else {
                patientService.createPhoto(photoRequest)
            }

            ServerResponse.ok().bodyValueAndAwait(photoRequest)
        } catch (e: NullPointerException) {
            ServerResponse.status(HttpStatus.BAD_REQUEST).buildAndAwait()
        }
    }

//    suspend fun findById(serverRequest: ServerRequest): ServerResponse {
//        val body = serverRequest.awaitMultipartData()
//        val userMultiPart = body["userType"]?.first() as FormFieldPart
//
//        val userId = UUID.fromString(serverRequest.pathVariable("id"))
//        val userType = getUserType(userMultiPart.content())
//
//        return try {
//            if (userType!! == "PSYCHOLOGIST") {
//                val photo = psychologistService.findPhotoById(userId)
//
//                ServerResponse.ok().bodyValueAndAwait(mapOf("photo" to photo))
//
//            } else {
//                ServerResponse.ok().buildAndAwait()
//            }
//
//        } catch (e: NullPointerException) {
//            ServerResponse.status(HttpStatus.BAD_REQUEST).buildAndAwait()
//        }
//
//    }


    suspend fun deleteById(serverRequest: ServerRequest): ServerResponse {
        val body = serverRequest.awaitMultipartData()
        val userMultiPart = body["userType"]?.first() as FormFieldPart

        val userId = UUID.fromString(serverRequest.pathVariable("id"))
        val userType = getUserType(userMultiPart.content())

        return try {
            if (userType!! == "PSYCHOLOGIST") {
                val photo = psychologistService.deletePhotoById(userId)

                ServerResponse.ok().buildAndAwait()

            } else {
                val photo = patientService.deletePhotoById(userId)

                ServerResponse.ok().buildAndAwait()
            }

        } catch (e: NullPointerException) {
            ServerResponse.status(HttpStatus.BAD_REQUEST).buildAndAwait()
        }
    }

    private fun getPhoto(dataBuffer: Flux<DataBuffer>): ByteArray? {
        return DataBufferUtils.join(dataBuffer).map {
            val byteArray = ByteArray(it.readableByteCount())

            it.read(byteArray)

            DataBufferUtils.release(it)

            byteArray
        }.share().block()
    }

    private fun getUserType(dataBuffer: Flux<DataBuffer>): String? {
        return DataBufferUtils.join(dataBuffer).map {
            val byteArray = ByteArray(it.readableByteCount())

            it.read(byteArray)

            DataBufferUtils.release(it)

            String(byteArray)
        }.share().block()
    }
}