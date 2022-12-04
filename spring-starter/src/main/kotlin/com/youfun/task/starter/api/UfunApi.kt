package com.youfun.task.starter.api

import com.youfun.task.core.dto.request.core.CronCallbackRequest
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * @author jackmiking
 * @date 2022/11/4
 */
@RestController
@RequestMapping("/ufun")
class UfunApi {

    @PostMapping("cronTask/execute")
    fun ufunEndpoint(@RequestBody cronCallbackRequest: CronCallbackRequest) {
        val time=LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        println("$time executing task :${cronCallbackRequest.name},${cronCallbackRequest.body}")
    }

}