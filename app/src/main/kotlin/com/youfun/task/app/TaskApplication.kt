package com.youfun.task.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author jackmiking
 * @date 2022/10/31
 */
@SpringBootApplication
open class TaskApplication

fun main(args: Array<String>) {
    runApplication<TaskApplication>(*args)
}
