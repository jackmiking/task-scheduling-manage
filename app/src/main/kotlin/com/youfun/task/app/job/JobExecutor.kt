package com.youfun.task.app.job

import com.youfun.task.core.dto.TaskInfo
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

interface JobExecutor {
    fun executeTask(taskInfo: TaskInfo)
}

class DefaultJobExecutor : JobExecutor {
    @Resource
    lateinit var executorService: ScheduledExecutorService
    override fun executeTask(taskInfo: TaskInfo) {
        executorService.schedule({
            try {
                var localTime = LocalTime.now()
                println("start execute")
                var pair = taskInfo.execute.execute()
                println("time:{},pair:{}".format(localTime.format(DateTimeFormatter.ISO_LOCAL_TIME), pair.toString()))
            } catch (e: Exception) {
                println(e.message)
            }
            if (taskInfo.plan.repeatExecutable()) {
                executeTask(taskInfo)
            }
        }, taskInfo.plan.calLatestExecuteTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
        println("god")
    }

}