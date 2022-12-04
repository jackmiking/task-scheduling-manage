package com.youfun.task.core.dto.request.core

import com.fasterxml.jackson.databind.JsonNode

/**
 * @author jackmiking
 * @date 2022/11/4
 */
class CronCallbackRequest(
    val name: String,
    val body: String? = null
) {

}