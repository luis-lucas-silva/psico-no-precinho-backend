package com.luislucassilva.psiconoprecinho.adapters.r2dbc.address

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.address.AddressSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.domain.address.Address
import com.luislucassilva.psiconoprecinho.ports.database.address.AddressRepository
import com.luislucassilva.psiconoprecinho.utils.bindOrNull
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open class AddressR2dbcRepository(
    private val databaseClient: DatabaseClient
): AddressRepository {

    override suspend fun create(address: Address): Address? {
        with(address) {
            return databaseClient.sql(INSERT)
                .bind("id", id.toString())
                .bind("logradouro", logradouro)
                .bindOrNull("numero", numero)
                .bindOrNull("complemento", complemento)
                .bind("cep", cep)
                .bind("bairro", bairro)
                .bind("cidade", cidade)
                .bind("estado", estado)
                .map(::rowMapper)
                .awaitOneOrNull()
        }

    }

    private fun rowMapper(row: Row): Address {
        return Address(
            id = UUID.fromString(row.get("idEndereco") as String),
            logradouro = row.get("Logradouro") as String,
            numero = row.get("Numero") as? Int,
            complemento = row.get("Complemento") as? String,
            cep = row.get("CEP") as String,
            bairro = row.get("Bairro") as String,
            cidade = row.get("Cidade") as String,
            estado = row.get("Estado") as String
        )
    }

}