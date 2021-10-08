package com.luislucassilva.psiconoprecinho.domain.administrator

import java.util.*

data class Administrator(
    val id: UUID? = null,
    val name: String,
    val email: String,
    val password: String
)