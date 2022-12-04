package com.youfun.task.starter

import com.youfun.task.core.client.TaskRegisterClient
import com.youfun.task.core.dto.*
import com.youfun.task.core.dto.request.app.AddTasksRequest
import com.youfun.task.starter.annotations.CronScheduled
import com.youfun.task.starter.config.UfunTaskProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import javax.annotation.Resource

@Component
class UfunTaskRegistrar(
    @Resource private val ufunTaskProperties: UfunTaskProperties,
    @Resource private val taskRegisterClient: TaskRegisterClient
) :
    ApplicationListener<ContextRefreshedEvent> {
    private val log: Logger = LoggerFactory.getLogger(UfunTaskRegistrar::class.java)
    private val name2MethodMap: MutableMap<String, Pair<Method, CronScheduled>>;

    init {
        name2MethodMap = HashMap()
    }

    fun registCronTask(cronScheduled: CronScheduled, method: Method) {
        if (cronScheduled.profiles.contains(ufunTaskProperties.appProfile)) {
            name2MethodMap[cronScheduled.name] = Pair(method, cronScheduled)
        } else {
            log.debug("cron task: ${cronScheduled.name} doesn't register in ${ufunTaskProperties.appProfile} profile")
        }
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {

        ufunTaskProperties.run {
            val appInfoList=name2MethodMap.map { it.value.second }.map { TaskInfo(it.name,CronTaskType(it.cron),
            UrlTaskExecutor(callbackHost+callbackPath,it.callbackBody)) }.toList()
            val addTasksRequest=AddTasksRequest(AppInfo(appName,appProfile,version,overwrite,callbackHost),appInfoList)
            taskRegisterClient.register(ufunTaskProperties.managerHost,addTasksRequest)
        }


    }


}