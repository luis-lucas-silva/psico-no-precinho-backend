package com.luislucassilva.psiconoprecinho.ports.database.address

import com.luislucassilva.psiconoprecinho.domain.address.Address

interface AddressRepository {
    suspend fun create(address: Address): Address?
}