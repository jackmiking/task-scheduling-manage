package com.youfun.task.app.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.youfun.task.app.job.DefaultJobSchedule
import com.youfun.task.app.job.JobSchedule
import org.reflections.Reflections
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor

@Configuration
open class AppConfiguration {
    @ConditionalOnMissingBean
    @Bean
    open fun jobExecutor(): JobSchedule {
        return DefaultJobSchedule()
    }

    @ConditionalOnMissingBean
    @Bean
    open fun scheduledExecutorService(): ScheduledExecutorService {
        return ScheduledThreadPoolExecutor(20);
    }



    @Bean
    open fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.serializationConfig.without(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .defaultPropertyInclusion.withValueInclusion(JsonInclude.Include.NON_NULL)
        val reflections= Reflections("com.youfun")
        val subClasses =reflections.getTypesAnnotatedWith(JsonTypeName::class.java)
        objectMapper.registerSubtypes(subClasses)
        return objectMapper
    }
}