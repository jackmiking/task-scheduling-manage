package com.youfun.task.core.dto.request.core

import com.fasterxml.jackson.databind.JsonNode

/**
 * @author jackmiking
 * @date 2022/11/4
 */
class EndpointRequest(
    val methodName: String,
    val jsonBody: JsonNode? = null
) {

}