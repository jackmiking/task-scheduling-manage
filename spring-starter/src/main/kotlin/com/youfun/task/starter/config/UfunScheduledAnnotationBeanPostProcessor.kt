package com.youfun.task.starter.config

import com.youfun.task.starter.UfunTaskRegistrar
import com.youfun.task.starter.annotations.CronScheduled
import org.springframework.aop.framework.AopProxyUtils
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.MethodIntrospector
import org.springframework.core.MethodIntrospector.MetadataLookup
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method
import javax.annotation.Resource

class UfunScheduledAnnotationBeanPostProcessor : BeanPostProcessor {
    @Resource
    lateinit var ufunTaskRegistrar: UfunTaskRegistrar;
    @Resource
    lateinit var ufunTaskProperties: UfunTaskProperties

    constructor() {

    }

    constructor(ufunTaskRegistrar: UfunTaskRegistrar) {
        this.ufunTaskRegistrar = ufunTaskRegistrar
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val targetClass = AopProxyUtils.ultimateTargetClass(bean)
        if (AnnotationUtils.isCandidateClass(targetClass, CronScheduled::class.java)) {
            val annotatedMethods = MethodIntrospector.selectMethods(targetClass,
                MetadataLookup { method: Method? ->
                    val scheduledAnnotation =
                        AnnotatedElementUtils.getMergedAnnotation(
                            method, CronScheduled::class.java
                        )
                    scheduledAnnotation
                } as MetadataLookup<CronScheduled>)
            if (!annotatedMethods.isEmpty()) {
                // Non-empty set of methods
                annotatedMethods.forEach {
                    ufunTaskRegistrar.registCronTask(it.value, it.key)
                }
            }
        }
        return bean
    }





}