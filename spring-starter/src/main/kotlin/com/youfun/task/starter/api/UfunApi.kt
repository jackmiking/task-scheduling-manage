package com.youfun.task.starter.api

import com.youfun.task.core.dto.request.core.CronCallbackRequest
import com.youfun.task.starter.UfunTaskRegistrar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author jackmiking
 * @date 2022/11/4
 */
@RestController
@RequestMapping("/ufun")
class UfunApi {
    @Autowired
    lateinit var ufunTaskRegistrar: UfunTaskRegistrar

    @PostMapping("cronTask/execute")
    fun ufunEndpoint(@RequestBody cronCallbackRequest: CronCallbackRequest) {
        ufunTaskRegistrar.runCronTask(cronCallbackRequest)
    }

}