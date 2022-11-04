package com.youfun.task.core.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author jackmiking
 * @date 2022/11/4
 */
@RequestMapping("/ufun")
class UfunApi {
    @PostMapping("")
    fun ufunEndpoint(){}

}