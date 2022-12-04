package com.youfun.task.starter.test

import com.youfun.task.starter.config.EnableUfunScheduling
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableUfunScheduling
@ComponentScan("com.youfun.task")
open class StarterApplication {
}

fun main(args: Array<String>) {
    runApplication<StarterApplication>(*args)
}