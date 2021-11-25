package com.luislucassilva.psiconoprecinho.adapters.router

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfig: WebFluxConfigurer
{
    override fun addCorsMappings(registry: CorsRegistry)
    {
        registry.addMapping("/**")
            .allowedOrigins("*") // any host or put domain(s) here
            .allowedMethods("GET", "POST", "PUT", "FETCH", "DELETE") // put the http verbs you want allow
            .allowedHeaders("*") // put the http headers you want allow
    }
    
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)
    }
}
