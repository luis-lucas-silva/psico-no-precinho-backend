package com.luislucassilva.psiconoprecinho.domain.photo

import java.util.*

data class PhotoRequest(
    val userId : UUID,
    val userType: PhotoRequestUserType,
    val photo: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhotoRequest

        if (userType != other.userType) return false
        if (!photo.contentEquals(other.photo)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = userType.hashCode()
        result = 31 * result + photo.contentHashCode()
        return result
    }
}

enum class PhotoRequestUserType {
    PSYCHOLOGIST, PATIENT
}