package com.luislucassilva.psiconoprecinho.services

import com.luislucassilva.psiconoprecinho.domain.patient.Patient
import com.luislucassilva.psiconoprecinho.domain.photo.PhotoRequest
import com.luislucassilva.psiconoprecinho.domain.search.SearchBuilder
import com.luislucassilva.psiconoprecinho.domain.search.SearchRequest
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

    suspend fun findById(id: UUID): Patient? {
        return patientRepository.findById(id)
    }

    suspend fun update(patient: Patient): Patient? {
        return patientRepository.update(patient)
    }

    suspend fun search(searchRequest: SearchRequest): List<Patient> {
        val values = searchRequest.values
        val search = SearchBuilder().fromFilters(values).build()

        return patientRepository.search(search)
    }

    suspend fun createPhoto(photoRequest: PhotoRequest) {
        patientRepository.createPhoto(photoRequest)
    }

    suspend fun deletePhotoById(userId: UUID) {
        patientRepository.deletePhotoById(userId)
    }
}