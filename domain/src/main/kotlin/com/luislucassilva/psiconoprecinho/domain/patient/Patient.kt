package com.luislucassilva.psiconoprecinho.domain.patient

import com.luislucassilva.psiconoprecinho.domain.address.Address
import com.luislucassilva.psiconoprecinho.domain.contact.Contact
import java.sql.Blob
import java.time.LocalDate
import java.util.*

data class Patient (
    val id: UUID? = null,
    val name: String,
    val document: String,
    val photo: ByteArray?,
    val birthdayDate: LocalDate,
    val gender: String,
    val email: String,
    val password: String,
    var address: Address,
    var contact: Contact
)
