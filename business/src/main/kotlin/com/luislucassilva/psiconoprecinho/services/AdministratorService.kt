package com.luislucassilva.psiconoprecinho.services

import com.luislucassilva.psiconoprecinho.ports.database.administrator.AdministratorRepository
import org.springframework.stereotype.Service

@Service
open class AdministratorService(
    val administratorRepository: AdministratorRepository
) {

}