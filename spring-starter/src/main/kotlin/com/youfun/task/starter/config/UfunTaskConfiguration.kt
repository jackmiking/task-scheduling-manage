package com.youfun.task.starter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
open class UfunTaskConfiguration {
    @Bean
    open fun ufunScheduledAnnotationBeanPostProcessor(): UfunScheduledAnnotationBeanPostProcessor {
        return UfunScheduledAnnotationBeanPostProcessor()
    }
}