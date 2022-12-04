package com.youfun.task.starter.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(UfunTaskConfiguration::class)
@EnableConfigurationProperties(UfunTaskProperties::class)
annotation class EnableUfunScheduling {
}