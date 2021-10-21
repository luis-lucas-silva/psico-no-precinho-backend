package com.luislucassilva.psiconoprecinho.adapters.router

import com.luislucassilva.psiconoprecinho.adapters.router.handlers.*
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
    fun homeRouter(handler: HomeHandler) = coRouter {
        GET("/", handler::getHome)
    }
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
    fun patientRouter(handler: PatientHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            getContextPath().nest {
                "/patient".nest {
                    POST("/login", handler::findByUserNameAndPasswordRequest)
                    POST("/search", handler::search)
                    GET("/{id:$UUID_REGEX}", handler::findById)
                    PUT("/{id:$UUID_REGEX}", handler::updateById)
                }
                POST("/patient", handler::create)

            }
        }
    }
    @Bean
    fun chatRouter(handler: ChatHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            getContextPath().nest {
                "/chat".nest {
                    GET("/{id:$UUID_REGEX}", handler::findById)
                    GET("/patient/{id:$UUID_REGEX}", handler::findByPatient)
                    GET("/psychologist/{id:$UUID_REGEX}", handler::findByPsychologist)
                    PUT("/{id:$UUID_REGEX}", handler::findMessages)
                }
                POST("/chat", handler::create)

            }
        }
    }
    @Bean
    fun messageRouter(handler: MessageHandler) = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            getContextPath().nest {
                "/message".nest {
                    GET("/chat/{id:$UUID_REGEX}", handler::findByChat)
                }
                POST("/message", handler::create)

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