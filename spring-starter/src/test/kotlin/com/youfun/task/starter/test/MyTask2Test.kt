package com.youfun.task.starter.test

import com.youfun.task.starter.annotations.CronScheduled
import org.springframework.stereotype.Component

@Component
class MyTask2Test {
    @CronScheduled("starter-2", "0/10 * * * * *", ["test", "prod"],"""{"hello":"hello2"}""")
    fun hello2(body: String) {
        println(body)
    }
}