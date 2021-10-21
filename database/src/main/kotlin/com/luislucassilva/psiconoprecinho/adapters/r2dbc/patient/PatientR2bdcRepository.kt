package com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient

import com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient.PatientSqlExpressions.FIND_BY_ID
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient.PatientSqlExpressions.FIND_BY_USERNAME_AND_PASSWORD
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient.PatientSqlExpressions.INSERT
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient.PatientSqlExpressions.SEARCH
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient.PatientSqlExpressions.UPDATE
import com.luislucassilva.psiconoprecinho.adapters.r2dbc.patient.PatientSqlExpressions.UPDATE_PHOTO_BY_ID
import com.luislucassilva.psiconoprecinho.domain.address.Address
import com.luislucassilva.psiconoprecinho.domain.contact.Contact
import com.luislucassilva.psiconoprecinho.domain.patient.Patient
import com.luislucassilva.psiconoprecinho.domain.photo.PhotoRequest
import com.luislucassilva.psiconoprecinho.ports.database.address.AddressRepository
import com.luislucassilva.psiconoprecinho.ports.database.contact.ContactRepository
import com.luislucassilva.psiconoprecinho.ports.database.patient.PatientRepository
import com.luislucassilva.psiconoprecinho.utils.bindIfNotNull
import com.luislucassilva.psiconoprecinho.utils.bindOrNull
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.toList
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.r2dbc.core.flow
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

        with(patient) {

            val address = addressRepository.create(address)
            val contact = contactRepository.create(contact)

            val patientInserted =  databaseClient.sql(INSERT)
                .bindIfNotNull("id", id.toString())
                .bind("name", name)
                .bind("document", document)
                .bindOrNull("photo", photo)
                .bind("birthdayDate", birthdayDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .bind("gender", gender)
                .bind("email", email)
                .bind("password", password)
                .bind("addressId", address!!.id.toString())
                .bind("contactId", contact!!.id.toString())
                .map(::rowMapper)
                .awaitOneOrNull()

            patientInserted?.address = address
            patientInserted?.contact = contact

            return patientInserted
        }

    }

    override suspend fun findById(id: UUID): Patient? {
        val patientFound = databaseClient.sql(FIND_BY_ID)
            .bind("id", id.toString())
            .map(::rowMapper)
            .awaitOneOrNull()

        return patientFound
    }

    override suspend fun update(patient: Patient): Patient?{


        with(patient) {
            val patientUpdated = databaseClient.sql(UPDATE)
                .bindIfNotNull("id", id.toString())
                .bind("name", name)
                .bind("document", document)
                .bindOrNull("photo", photo)
                .bind("birthdayDate", birthdayDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
                .bind("gender", gender)
                .bind("email", email)
                .bind("password", password)
                .map(::rowMapper)
                .awaitOneOrNull()

            patientUpdated?.address = addressRepository.update(address)!!
            patientUpdated?.contact = contactRepository.update(contact)!!

            return patientUpdated
        }
    }

    override suspend fun search(search: String): List<Patient> {
        val patients = databaseClient.sql(SEARCH + search)
            .map(::rowMapper)
            .flow()
            .toList()

        databaseClient.sql(SEARCH)
            .bind("search", search).toString()

        return patients
    }

    override suspend fun createPhoto(photoRequest: PhotoRequest) {
        databaseClient.sql(UPDATE_PHOTO_BY_ID)
            .bind("photo", photoRequest.photo)
            .bind("id", photoRequest.userId.toString())
            .await()
    }

    override suspend fun deletePhotoById(id: UUID) {
        val nullValue: Any? = null
        databaseClient.sql(UPDATE_PHOTO_BY_ID)
            .bindOrNull("photo", nullValue)
            .bind("id", id.toString())
            .await()
    }

    private fun rowMapper(row: Row): Patient {
        return Patient(
            id = UUID.fromString(row.get("idPaciente") as String),
            name = row.get("NomePaciente") as String,
            document = row.get("Documento") as String,
            photo = row.get("Foto") as? ByteArray,
            birthdayDate = row.get("Nascimento") as LocalDate,
            gender = row.get("Genero") as String,
            email = row.get("Email") as String,
            password = row.get("Senha") as String,
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