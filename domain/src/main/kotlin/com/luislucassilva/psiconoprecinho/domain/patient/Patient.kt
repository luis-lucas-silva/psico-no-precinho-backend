package com.luislucassilva.psiconoprecinho.domain.patient

import com.luislucassilva.psiconoprecinho.domain.address.Address
import com.luislucassilva.psiconoprecinho.domain.contact.Contact
import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist
import java.sql.Blob
import java.time.LocalDate
import java.util.*

data class Patient (
    val id: UUID? = null,
    val name: String,
    val document: String,
    val documentType: String,
    val photo: ByteArray?,
    val birthdayDate: LocalDate,
    val gender: String,
    val description: String,
    val email: String,
    val password: String,
    val status: String = "PENDENTE",
    val address: Address,
    val contact: Contact
){
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Patient

        if (id != other.id) return false
        if (name != other.name) return false
        if (document != other.document) return false
        if (documentType != other.documentType) return false
        if (photo != null) {
            if (other.photo == null) return false
            if (!photo.contentEquals(other.photo)) return false
        } else if (other.photo != null) return false
        if (birthdayDate != other.birthdayDate) return false
        if (gender != other.gender) return false
        if (description != other.description) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (status != other.status) return false
        if (address != other.address) return false
        if (contact != other.contact) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + document.hashCode()
        result = 31 * result + documentType.hashCode()
        result = 31 * result + (photo?.contentHashCode() ?: 0)
        result = 31 * result + birthdayDate.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + contact.hashCode()
        return result
    }
}