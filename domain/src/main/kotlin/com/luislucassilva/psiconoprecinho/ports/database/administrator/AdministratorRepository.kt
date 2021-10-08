package com.luislucassilva.psiconoprecinho.ports.database.administrator

import com.luislucassilva.psiconoprecinho.domain.administrator.Administrator
import java.util.*

interface AdministratorRepository {

    suspend fun findByUserNameAndPassword(administrator: Administrator): Administrator?
    suspend fun create(administrator: Administrator): Administrator?
    suspend fun update(administrator: Administrator): Administrator?
    suspend fun findById(id: UUID): Administrator?

}