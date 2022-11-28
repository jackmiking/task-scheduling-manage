package com.youfun.task.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author jackmiking
 * @date 2022/10/31
 */
@SpringBootApplication
@EnableScheduling
open class TaskApplication

fun main(args: Array<String>) {
    runApplication<TaskApplication>(*args)
}
