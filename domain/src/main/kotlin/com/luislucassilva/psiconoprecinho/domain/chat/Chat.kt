package com.luislucassilva.psiconoprecinho.domain.chat

import com.luislucassilva.psiconoprecinho.domain.patient.Patient
import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist
import java.util.*

data class Chat (
    val id: UUID? = null,
    val patient: UUID? = null,
    val psychologist: UUID? = null,
    var messages: List<Message> = emptyList()
    )