package com.luislucassilva.psiconoprecinho.domain.chat

import com.luislucassilva.psiconoprecinho.domain.patient.Patient
import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Message(
    val id: UUID? = null,
    val content: String,
    val date: LocalDateTime,
    val chat: UUID? = null,
    val sender: UUID? = null,
    val receiver: UUID? = null,
    val read: Boolean = false
)
