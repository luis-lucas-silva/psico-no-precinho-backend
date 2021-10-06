package com.luislucassilva.psiconoprecinho.domain.psychologist

import com.luislucassilva.psiconoprecinho.domain.address.Address
import com.luislucassilva.psiconoprecinho.domain.contact.Contact
import java.sql.Blob
import java.time.LocalDate
import java.util.*


data class Psychologist(
    val id: UUID? = null,
    val name: String,
    val document: String,
    val documentType: String,
    val photo: ByteArray?,
    val crp: String,
    val birthdayDate: LocalDate,
    val gender: String,
    val minValue: Double,
    val maxValue: Double,
    val description: String,
    val email: String,
    val password: String,
    val status: String = "PENDENTE",
    var address: Address,
    var contact: Contact,
    var themes: List<Theme>? = null,
    var formacao: List<Formacao>? = null
)

data class Formacao(
    val id: UUID? = UUID.randomUUID(),
    val name: String
)