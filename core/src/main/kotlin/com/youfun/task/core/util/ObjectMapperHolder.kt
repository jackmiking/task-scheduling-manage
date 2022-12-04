package com.youfun.task.core.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

object ObjectMapperHolder {
    val objectMapper: ObjectMapper = ObjectMapper();

    init {
        val objectMapper = ObjectMapper()
        objectMapper.serializationConfig.without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .defaultPropertyInclusion.withValueInclusion(JsonInclude.Include.NON_NULL)

    }


}
