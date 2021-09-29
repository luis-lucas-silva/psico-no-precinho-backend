package com.luislucassilva.psiconoprecinho.adapters.r2dbc.theme

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.formacao.FormacaoSqlExpressions
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.theme.ThemeSqlExpressions.DELETE
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.theme.ThemeSqlExpressions.FIND_BY_PSYCHOLOGIST_ID
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.theme.ThemeSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.domain.psychologist.Theme
import com.luislucassilva.psiconoprecinho.ports.database.theme.ThemeRepository
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.toList
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.flow
import sun.awt.windows.ThemeReader.getEnum
import java.util.*

class ThemeR2dbcRepository(
    private val databaseClient: DatabaseClient
) : ThemeRepository {
    override suspend fun addThemeToPsychologist(themeId: Int, psychologistId: UUID) {
        databaseClient.sql(INSERT)
            .bind("idPsicologo", psychologistId)
            .bind("idTema", themeId)
            .await()
    }

    override suspend fun deleteAllThemesToPsychologist(psychologistId: UUID) {
        databaseClient.sql(DELETE)
            .bind("idPsicologo", psychologistId)
            .await()
    }

    override suspend fun findByPsychologistId(psychologistId: UUID): List<Theme> {
        return databaseClient.sql(FIND_BY_PSYCHOLOGIST_ID)
            .bind("idPsicologo", psychologistId)
            .map(::rowMapper)
            .flow()
            .toList()

    }

    private fun rowMapper(row: Row): Theme {
        return Theme.getEnum(row.get("Tema_idTema") as Int)
    }
}