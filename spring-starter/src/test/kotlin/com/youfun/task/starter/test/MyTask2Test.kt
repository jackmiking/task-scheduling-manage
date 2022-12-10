package com.youfun.task.starter.test

import com.youfun.task.core.dto.request.core.CronCallbackRequest
import com.youfun.task.starter.annotations.CronScheduled
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestParam
import java.util.logging.Logger

@Component
class MyTask2Test {
    val logger =Logger.getLogger("hello")
    @CronScheduled("starter-2", "0/10 * * * * *", ["test", "prod"],"""{"hello":"hello2"}""")
    fun hello2(@RequestParam(name = "body") body: String,@RequestParam("name") name: String,callbackRequest: CronCallbackRequest) {
        logger.info(String.format("hello2: name:%s,body:%s, request:%s",name,body,callbackRequest))
    }
}