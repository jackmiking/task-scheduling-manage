package com.youfun.task.starter

import com.youfun.task.core.client.TaskRegisterClient
import com.youfun.task.core.dto.AppInfo
import com.youfun.task.core.dto.CronTaskType
import com.youfun.task.core.dto.TaskInfo
import com.youfun.task.core.dto.UrlTaskExecutor
import com.youfun.task.core.dto.request.app.AddTasksRequest
import com.youfun.task.core.dto.request.core.CronCallbackRequest
import com.youfun.task.starter.annotations.CronScheduled
import com.youfun.task.starter.config.UfunTaskProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestParam
import java.lang.reflect.Method

@Component
class UfunTaskRegistrar(
    @Autowired private val ufunTaskProperties: UfunTaskProperties,
    @Autowired private val taskRegisterClient: TaskRegisterClient
) :
    ApplicationListener<ContextRefreshedEvent> {
    private val log: Logger = LoggerFactory.getLogger(UfunTaskRegistrar::class.java)
    private val name2MethodMap: MutableMap<String, Triple<Method, CronScheduled, Any>>;

    init {
        name2MethodMap = HashMap()
    }

    fun registCronTask(cronScheduled: CronScheduled, method: Method, bean: Any) {
        if (cronScheduled.profiles.contains(ufunTaskProperties.appProfile)) {
            name2MethodMap[cronScheduled.name] = Triple(method, cronScheduled, bean)
        } else {
            log.debug("cron task: ${cronScheduled.name} doesn't register in ${ufunTaskProperties.appProfile} profile")
        }
    }

    fun runCronTask(callbackRequest: CronCallbackRequest) {
        val pair = name2MethodMap.get(callbackRequest.name)
        if (pair != null) {
            val method = pair.first
            method.invoke(pair.third, *parseMethodParam(method, callbackRequest).toArray())
            log.info(
                "cronTask/{}: {}.{}: cronTask runed.",
                callbackRequest.name,
                pair.third.javaClass.simpleName,
                method.name
            )
        } else {
            log.error("cronTask/%s: unknow cron task.", callbackRequest.name)
        }
    }

    fun parseMethodParam(method: Method, callbackRequest: CronCallbackRequest): ArrayList<Any?> {
        val num = method.parameterCount
        val parameters = method.parameters
        val params = ArrayList<Any?>(num)
        var i = 0;
        while (i < num) {
            val parammeter = parameters.get(i++)
            if (parammeter.type == String::class || parammeter.type == String::class.java) {
                if (parammeter.isAnnotationPresent(RequestParam::class.java)) {
                    val requestParam: RequestParam = parammeter.getAnnotation(RequestParam::class.java)
                    if (requestParam.name == "body") {
                        params.add(callbackRequest.body)
                    } else if (requestParam.name == "name") {
                        params.add(callbackRequest.name)
                    } else {
                        params.add("")
                    }
                } else {
                    params.add("")
                }
            } else if (parammeter.type.equals(CronCallbackRequest::class) || parammeter.type.equals(CronCallbackRequest::class.java)) {
                params.add(callbackRequest)
            } else {
                params.add(null)
            }
        }
        return params
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {

        ufunTaskProperties.run {
            val appInfoList = name2MethodMap.map { it.value.second }.map {
                TaskInfo(
                    it.name, CronTaskType(it.cron),
                    UrlTaskExecutor(callbackHost + callbackPath, it.callbackBody)
                )
            }.toList()
            val addTasksRequest =
                AddTasksRequest(AppInfo(appName, appProfile, version, overwrite, callbackHost), appInfoList)
            taskRegisterClient.register(ufunTaskProperties.managerHost, addTasksRequest)
        }


    }


}