package com.luislucassilva.psiconoprecinho.adapters.router

import com.luislucassilva.psiconoprecinho.adapters.router.handlers.AdministratorHandler
import com.luislucassilva.psiconoprecinho.adapters.router.handlers.PhotoHandler
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
    fun psychologistRouter(handler: PsychologistHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            getContextPath().nest {
                "/psychologist".nest {
                    POST("/login", handler::findByUserNameAndPasswordRequest)
                    POST("/search", handler::search)
                    GET("/{id:$UUID_REGEX}", handler::findById)
                    PUT("/{id:$UUID_REGEX}", handler::updateById)
                }
                POST("/psychologist", handler::create)

            }
        }
    }

    @Bean
    fun photoRouter(handler: PhotoHandler) = coRouter {
        accept(MediaType.MULTIPART_FORM_DATA).nest {
            getContextPath().nest {
                "/photo".nest {
                    "/psychologist".nest {
                        POST("/{id:$UUID_REGEX}", handler::createOrUpdate)
//                        GET("/{id:$UUID_REGEX}", handler::findById)
                        DELETE("/{id:$UUID_REGEX}", handler::deleteById)
                    }
                }
            }
        }
    }

    @Bean
    fun administratorRouter(handler: AdministratorHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            getContextPath().nest {
                "/administrator".nest {
                    POST("/login", handler::findByUserNameAndPasswordRequest)
                    GET("/{id:$UUID_REGEX}", handler::findById)
                    PUT("/{id:$UUID_REGEX}", handler::updateById)
                }
                POST("/administrator", handler::create)
            }
        }
    }
}