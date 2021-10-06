package com.luislucassilva.psiconoprecinho.adapters.r2dbc.formacao

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.formacao.FormacaoSqlExpressions.DELETE
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.formacao.FormacaoSqlExpressions.FIND_BY_PSYCHOLOGIST_ID
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.formacao.FormacaoSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.domain.psychologist.Formacao
import com.luislucassilva.psiconoprecinho.ports.database.formacao.FormacaoRepository
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.toList
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.flow
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open class FormacaoR2dbcRepository(
    private val databaseClient: DatabaseClient
) : FormacaoRepository {
    override suspend fun create(formacao: Formacao, psychologistId: UUID) {

        databaseClient.sql(INSERT)
            .bind("id", formacao.id.toString())
            .bind("name", formacao.name)
            .bind("psychologistId", psychologistId.toString())
            .await()
    }

    override suspend fun delete(psychologistId: UUID) {
        databaseClient.sql(DELETE)
            .bind("psychologistId", psychologistId.toString())
            .await()
    }

    override suspend fun findByPsychologistId(psychologistId: UUID): List<Formacao> {
        return databaseClient.sql(FIND_BY_PSYCHOLOGIST_ID)
            .bind("psychologistId", psychologistId.toString())
            .map(::rowMapper)
            .flow()
            .toList()
    }

    private fun rowMapper(row: Row): Formacao {
        return Formacao(
            id = UUID.fromString(row.get("idFormacao") as String),
            name = row.get("Formacao") as String
        )
    }

}