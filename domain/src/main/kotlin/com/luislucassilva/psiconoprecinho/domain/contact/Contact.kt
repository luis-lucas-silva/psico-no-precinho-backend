package com.luislucassilva.psiconoprecinho.domain.contact

import java.util.*

data class Contact(
    val id: UUID = UUID.randomUUID(),
    val type: String,
    val number: String
)
