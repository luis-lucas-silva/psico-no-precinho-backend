package com.luislucassilva.psiconoprecinho.adapters.router

import com.luislucassilva.psiconoprecinho.adapters.router.handlers.PsychologistHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {

    companion object {
        private const val UUID_REGEX =
            "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"
    }

    private fun getContextPath() = "/api/"

    @Bean
    fun psychologistLoginRouter(handler: PsychologistHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            getContextPath().nest {
                "/psychologist".nest {
                    POST("/login", handler::findByUserNameAndPasswordRequest)
                }
                POST("/psychologist", handler::create)
            }
        }
    }
}