package com.luislucassilva.psiconoprecinho.services

import com.luislucassilva.psiconoprecinho.domain.patient.Patient
import com.luislucassilva.psiconoprecinho.ports.database.patient.PatientRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class PatientService(
    private val patientRepository: PatientRepository
){
    suspend fun findByUserNameAndPassword(userName: String, password: String): Patient?{
        return patientRepository.findByUserNameAndPassword(userName, password)
    }

    suspend fun create(patient: Patient): Patient? {
        return patientRepository.create(patient.copy(id = UUID.randomUUID()))
    }
}