package com.luislucassilva.psiconoprecinho.ports.database.formacao

import com.luislucassilva.psiconoprecinho.domain.psychologist.Formacao
import java.util.*

interface FormacaoRepository {
    suspend fun create(formacao: Formacao, psychologistId: UUID)
    suspend fun delete(psychologistId: UUID)
    suspend fun findByPsychologistId(psychologistId: UUID): List<Formacao>
}