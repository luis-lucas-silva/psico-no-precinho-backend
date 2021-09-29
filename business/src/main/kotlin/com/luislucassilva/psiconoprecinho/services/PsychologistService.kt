package com.luislucassilva.psiconoprecinho.services

import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist
import com.luislucassilva.psiconoprecinho.ports.database.psychologist.PsychologistRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PsychologistService(
    private val psychologistRepository: PsychologistRepository
) {

    suspend fun findByUserNameAndPassword(userName: String, password: String): Psychologist? {
        return psychologistRepository.findByUserNameAndPassword(userName, password)
    }

    suspend fun create(psychologist: Psychologist): Psychologist? {
        return psychologistRepository.create(psychologist.copy(id = UUID.randomUUID()))
    }

    suspend fun findById(id: UUID): Psychologist? {
        return psychologistRepository.findById(id)
    }
}