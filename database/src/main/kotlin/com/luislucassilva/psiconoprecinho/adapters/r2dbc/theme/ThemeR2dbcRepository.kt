package com.luislucassilva.psiconoprecinho.adapters.r2dbc.theme

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
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open class ThemeR2dbcRepository(
    private val databaseClient: DatabaseClient
) : ThemeRepository {
    override suspend fun addThemeToPsychologist(themeId: Int, psychologistId: UUID) {
        databaseClient.sql(INSERT)
            .bind("idPsicologo", psychologistId.toString())
            .bind("idTema", themeId.toString())
            .await()
    }

    override suspend fun deleteAllThemesToPsychologist(psychologistId: UUID) {
        databaseClient.sql(DELETE)
            .bind("idPsicologo", psychologistId.toString())
            .await()
    }

    override suspend fun findByPsychologistId(psychologistId: UUID): List<Theme> {
        return databaseClient.sql(FIND_BY_PSYCHOLOGIST_ID)
            .bind("idPsicologo", psychologistId.toString())
            .map(::rowMapper)
            .flow()
            .toList()

    }

    private fun rowMapper(row: Row): Theme {
        return Theme.getEnum(row.get("Tema_idTema") as Int)
    }
}