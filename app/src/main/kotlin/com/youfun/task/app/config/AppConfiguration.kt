package com.youfun.task.app.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.youfun.task.app.job.DefaultJobExecutor
import com.youfun.task.app.job.JobExecutor
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor

@Configuration
open class AppConfiguration {
    @ConditionalOnMissingBean
    @Bean
    open fun jobExecutor(): JobExecutor {
        return DefaultJobExecutor()
    }

    @Bean
    open fun scheduledExecutorService(): ScheduledExecutorService {
        return ScheduledThreadPoolExecutor(10);
    }

    @Bean
    open fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.serializationConfig.without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .defaultPropertyInclusion.withValueInclusion(JsonInclude.Include.NON_NULL)
        return objectMapper
    }
}