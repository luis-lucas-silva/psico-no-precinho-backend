package com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient.PatientSqlExpressions.FIND_BY_USERNAME_AND_PASSWORD
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient.PatientSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.domain.address.Address
import com.luislucassilva.psiconoprecinho.domain.contact.Contact
import com.luislucassilva.psiconoprecinho.domain.patient.Patient
import com.luislucassilva.psiconoprecinho.ports.database.address.AddressRepository
import com.luislucassilva.psiconoprecinho.ports.database.contact.ContactRepository
import com.luislucassilva.psiconoprecinho.ports.database.patient.PatientRepository
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
open class PatientR2bdcRepository(
    private val databaseClient: DatabaseClient,
    private val addressRepository: AddressRepository,
    private val contactRepository: ContactRepository
): PatientRepository {

    override suspend fun findByUserNameAndPassword(userName: String, password: String): Patient? {
        return databaseClient.sql(FIND_BY_USERNAME_AND_PASSWORD)
            .bind("username", userName)
            .bind("password", password)
            .map(::rowMapper)
            .awaitOneOrNull()

    }

    override suspend fun create(patient: Patient): Patient?{
        val address = addressRepository.create(patient.address)
        val contact = contactRepository.create(patient.contact)

        with(patient) {
            return databaseClient.sql(INSERT)
                .bindIfNotNull("id", id.toString())
                .bind("name", name)
                .bind("document", document)
                .bind("documentType", documentType)
                .bindOrNull("photo", photo)
                .bind("birthdayDate", birthdayDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .bind("gender", gender)
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

    private fun rowMapper(row: Row): Patient {
        return Patient(
            id = UUID.fromString(row.get("idPaciente") as String),
            name = row.get("NomePaciente") as String,
            documentType = row.get("TipoDocumento") as String,
            document = row.get("Documento") as String,
            photo = row.get("Foto") as? ByteArray,
            birthdayDate = row.get("Nascimento") as LocalDate,
            gender = row.get("Genero") as String,
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