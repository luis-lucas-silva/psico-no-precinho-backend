package com.luislucassilva.psiconoprecinho.adapters.r2dbc.psychologist

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.psychologist.PsychologistSqlExpressions.FIND_BY_USERNAME_AND_PASSWORD
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.psychologist.PsychologistSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.domain.address.Address
import com.luislucassilva.psiconoprecinho.domain.contact.Contact
import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist
import com.luislucassilva.psiconoprecinho.ports.database.address.AddressRepository
import com.luislucassilva.psiconoprecinho.ports.database.contact.ContactRepository
import com.luislucassilva.psiconoprecinho.ports.database.psychologist.PsychologistRepository
import com.luislucassilva.psiconoprecinho.utils.bindIfNotNull
import com.luislucassilva.psiconoprecinho.utils.bindOrNull
import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository
import java.sql.Blob
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Repository
open class PsychologistR2dbcRepository(
    private val databaseClient: DatabaseClient,
    private val addressRepository: AddressRepository,
    private val contactRepository: ContactRepository
): PsychologistRepository {

    override suspend fun findByUserNameAndPassword(userName: String, password: String): Psychologist? {
        return databaseClient.sql(FIND_BY_USERNAME_AND_PASSWORD)
            .bind("username", userName)
            .bind("password", password)
            .map(::rowMapper)
            .awaitOneOrNull()

    }

    override suspend fun create(psychologist: Psychologist): Psychologist? {
        val address = addressRepository.create(psychologist.address)
        val contact = contactRepository.create(psychologist.contact)

        with(psychologist) {
            return databaseClient.sql(INSERT)
                .bindIfNotNull("id", id.toString())
                .bind("name", name)
                .bind("document", document)
                .bind("documentType", documentType)
                .bindOrNull("photo", photo)
                .bind("crp", crp)
                .bind("birthdayDate", birthdayDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .bind("gender", gender)
                .bind("minValue", minValue)
                .bind("maxValue", maxValue)
                .bind("description", description)
                .bind("email", email)
                .bind("password", password)
                .bind("status", status)
                .bind("addressId", address!!.id.toString())
                .bind("contactId", contact!!.id.toString())
                .map(::rowMapper)
                .awaitOneOrNull()
        }

    }

    private fun rowMapper(row: Row): Psychologist {
        return Psychologist(
            id = UUID.fromString(row.get("idPsicologo") as String),
            name = row.get("NomePsicologo") as String,
            documentType = row.get("TipoDocumento") as String,
            document = row.get("Documento") as String,
            photo = row.get("Foto") as? ByteArray,
            crp = row.get("CRP") as String,
            birthdayDate = row.get("Nascimento") as LocalDate,
            gender = row.get("Genero") as String,
            minValue = row.get("ValorMin") as Double,
            maxValue = row.get("ValorMax") as Double,
            description = row.get("Descricao") as String,
            email = row.get("Email") as String,
            password = row.get("Senha") as String,
            status = row.get("StatusCadastro") as String,
            address = rowMapperAddress(row),
            contact = rowMapperContact(row)
        )
    }

    private fun rowMapperAddress(row: Row): Address {
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

    private fun rowMapperContact(row: Row): Contact {
        return Contact(
            id = UUID.fromString(row.get("idContato") as String),
            type = row.get("Tipo") as String,
            number = row.get("Telefone") as String
        )
    }
}