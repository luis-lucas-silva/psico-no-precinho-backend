package com.luislucassilva.psiconoprecinho.ports.database.psychologist

import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist

interface PsychologistRepository {
    suspend fun findByUserNameAndPassword(userName: String, password: String): Psychologist?
    suspend fun create(psychologist: Psychologist): Psychologist?
}