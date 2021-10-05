package com.luislucassilva.psiconoprecinho.ports.database.contact

import com.luislucassilva.psiconoprecinho.domain.contact.Contact

interface ContactRepository {
    suspend fun create(contact: Contact): Contact?
    suspend fun update(contact: Contact): Contact?
}