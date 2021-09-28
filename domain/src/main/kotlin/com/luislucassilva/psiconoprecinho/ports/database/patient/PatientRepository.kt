package com.luislucassilva.psiconoprecinho.ports.database.patient

import com.luislucassilva.psiconoprecinho.domain.patient.Patient

interface PatientRepository {
    suspend fun findByUserNameAndPassword(userName: String, password: String): Patient?
    suspend fun create(patient: Patient): Patient?
}