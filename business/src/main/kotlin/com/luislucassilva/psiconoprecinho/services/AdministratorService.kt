package com.luislucassilva.psiconoprecinho.services

import com.luislucassilva.psiconoprecinho.domain.administrator.Administrator
import com.luislucassilva.psiconoprecinho.ports.database.administrator.AdministratorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
open class AdministratorService(
    private val administratorRepository: AdministratorRepository
) {
    suspend fun findByUserNameAndPassword(userName: String, password: String): Administrator? {
        return administratorRepository.findByUserNameAndPassword(userName, password)
    }

    suspend fun findById(id: UUID): Administrator? {
        return administratorRepository.findById(id)
    }
}