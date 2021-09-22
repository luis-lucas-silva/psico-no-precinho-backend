package com.luislucassilva.psiconoprecinho.domain.login

import java.util.regex.Pattern
import kotlin.jvm.Throws

data class LoginRequest(
    val userName: String?,
    val password: String?
) {
    companion object {
        private val EMAIL_REGEX = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
        private val PASSWORD_REGEX = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}")
    }

    fun isValid(): Boolean {
        return when {
            isUserNameAndPasswordNullOrEmpty() -> false
            isValidPassword()!! -> true
            isValidUserName() -> true
            else -> false
        }
    }

    private fun isValidUserName() = userName?.let { EMAIL_REGEX.matcher(it).matches() } == true
    private fun isValidPassword() = password?.let { PASSWORD_REGEX.matcher(it).matches() } == true
    private fun isUserNameAndPasswordNullOrEmpty() = userName.isNullOrEmpty() || password.isNullOrEmpty()
}
