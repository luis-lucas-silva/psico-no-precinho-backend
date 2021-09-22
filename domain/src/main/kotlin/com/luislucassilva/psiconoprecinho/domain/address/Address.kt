package com.luislucassilva.psiconoprecinho.domain.address

import java.util.*

data class Address(
    val id: UUID = UUID.randomUUID(),
    val logradouro: String,
    val numero: Int?,
    val complemento: String?,
    val cep: String,
    val bairro: String,
    val cidade: String,
    val estado: String
)
