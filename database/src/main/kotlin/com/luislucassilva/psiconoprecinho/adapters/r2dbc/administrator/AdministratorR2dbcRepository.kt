package com.luislucassilva.psiconoprecinho.adapters.r2dbc.administrator

import com.luislucassilva.psiconoprecinho.domain.administrator.Administrator
import com.luislucassilva.psiconoprecinho.ports.database.administrator.AdministratorRepository
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open class AdministratorR2dbcRepository(
    private val databaseClient: DatabaseClient
) : AdministratorRepository {
    override suspend fun findByUserNameAndPassword(administrator: Administrator): Administrator? {
        TODO("Not yet implemented")
    }

    override suspend fun create(administrator: Administrator): Administrator? {
        TODO("Not yet implemented")
    }

    override suspend fun update(administrator: Administrator): Administrator? {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: UUID): Administrator? {
        TODO("Not yet implemented")
    }
}