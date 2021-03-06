package com.luislucassilva.psiconoprecinho

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@ComponentScan("com.luislucassilva.psiconoprecinho.*")
@EnableWebFlux
@CrossOrigin
class PsicoNoPrecinhoApplication {}

fun main(args: Array<String>) {
    runApplication<PsicoNoPrecinhoApplication>(*args)
}


