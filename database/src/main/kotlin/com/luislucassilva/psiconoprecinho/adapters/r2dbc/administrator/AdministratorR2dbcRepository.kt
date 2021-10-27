package com.luislucassilva.psiconoprecinho.adapters.r2dbc.administrator

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.administrator.AdministratorSqlExpressions.FIND_BY_ID
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.administrator.AdministratorSqlExpressions.FIND_BY_USERNAME_AND_PASSWORD
import com.luislucassilva.psiconoprecinho.domain.administrator.Administrator
import com.luislucassilva.psiconoprecinho.ports.database.administrator.AdministratorRepository
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open class AdministratorR2dbcRepository(
    private val databaseClient: DatabaseClient
) : AdministratorRepository {
    override suspend fun findByUserNameAndPassword(userName: String, password: String): Administrator? {
        return databaseClient.sql(FIND_BY_USERNAME_AND_PASSWORD)
            .bind("username", userName)
            .bind("password", password)
            .map(::rowMapper)
            .awaitOneOrNull()
    }

    override suspend fun create(administrator: Administrator): Administrator? {
        TODO("Not yet implemented")
    }

    override suspend fun update(administrator: Administrator): Administrator? {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: UUID): Administrator? {
        return databaseClient.sql(FIND_BY_ID)
            .bind("id", id)
            .map(::rowMapper)
            .awaitOneOrNull()
    }

    private fun rowMapper(row: Row): Administrator {
        return Administrator(
            id = UUID.fromString(row.get("id") as String),
            name = row.get("nome") as String,
            email = row.get("email") as String,
            password = row.get("senha") as String
        )
    }
}