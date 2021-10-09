package com.luislucassilva.psiconoprecinho.ports.database.patient

import com.luislucassilva.psiconoprecinho.domain.patient.Patient
import com.luislucassilva.psiconoprecinho.domain.photo.PhotoRequest
import java.util.*

interface PatientRepository {
    suspend fun findByUserNameAndPassword(userName: String, password: String): Patient?
    suspend fun create(patient: Patient): Patient?
    suspend fun findById(id: UUID): Patient?
    suspend fun update(patient: Patient): Patient?
    suspend fun search(search: String): List<Patient>

    suspend fun createPhoto(photoRequest: PhotoRequest)
    suspend fun deletePhotoById(id: UUID)
}