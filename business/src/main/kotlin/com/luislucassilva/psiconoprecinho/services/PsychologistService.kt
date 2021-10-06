package com.luislucassilva.psiconoprecinho.services

import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist
import com.luislucassilva.psiconoprecinho.domain.search.SearchBuilder
import com.luislucassilva.psiconoprecinho.domain.search.SearchRequest
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

    suspend fun update(psychologist: Psychologist): Psychologist? {
        return psychologistRepository.update(psychologist)
    }

    suspend fun search(searchRequest: SearchRequest): List<Psychologist> {
        val values = searchRequest.values
        val search = SearchBuilder().fromFilters(values).build()

        return psychologistRepository.search(search)
    }
}


