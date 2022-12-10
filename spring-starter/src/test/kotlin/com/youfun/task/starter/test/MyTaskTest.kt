package com.youfun.task.starter.test

import com.youfun.task.starter.annotations.CronScheduled
import org.springframework.stereotype.Component

@Component
class MyTaskTest {

    @CronScheduled("starter-1", "0/10 * * * * *",["test","prod"])
    fun hell0(name:String,body: String) {
        println(String.format("hello: name:%s,body:%s",name,body))
    }
}