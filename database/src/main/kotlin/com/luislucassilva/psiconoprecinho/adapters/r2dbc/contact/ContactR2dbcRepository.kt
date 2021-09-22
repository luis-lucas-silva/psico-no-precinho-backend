package com.luislucassilva.psiconoprecinho.adapters.r2dbc.contact

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.contact.ContactSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.domain.contact.Contact
import com.luislucassilva.psiconoprecinho.ports.database.contact.ContactRepository
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open class ContactR2dbcRepository(
    private val databaseClient: DatabaseClient
) : ContactRepository {

    override suspend fun create(contact: Contact): Contact? {
        with(contact) {
            return databaseClient.sql(INSERT)
                .bind("id", id.toString())
                .bind("type", type)
                .bind("number", number)
                .map(::rowMapper)
                .awaitOneOrNull()
        }

    }

    private fun rowMapper(row: Row): Contact {
        return Contact(
            id = UUID.fromString(row.get("idContato") as String),
            type = row.get("Tipo") as String,
            number = row.get("Telefone") as String
        )
    }
}