package com.luislucassilva.psiconoprecinho.ports.database.psychologist

import com.luislucassilva.psiconoprecinho.domain.photo.PhotoRequest
import com.luislucassilva.psiconoprecinho.domain.psychologist.Psychologist
import java.util.*

interface PsychologistRepository {
    suspend fun findByUserNameAndPassword(userName: String, password: String): Psychologist?
    suspend fun create(psychologist: Psychologist): Psychologist?
    suspend fun findById(id: UUID): Psychologist?
    suspend fun update(psychologist: Psychologist): Psychologist?
    suspend fun search(search: String): List<Psychologist>

    suspend fun createPhoto(photoRequest: PhotoRequest)
    suspend fun deletePhotoById(id: UUID)

    suspend fun findByPendingStatus(): List<Psychologist>
}