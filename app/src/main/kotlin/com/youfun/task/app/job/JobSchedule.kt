package com.youfun.task.app.job

import com.fasterxml.jackson.databind.ObjectMapper
import com.youfun.task.app.entity.CronTask
import com.youfun.task.app.entity.OneTimeTask
import com.youfun.task.core.dto.*
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

interface JobSchedule {

    fun scheduleOneTimeTask(oneTimeTask: OneTimeTask)
    fun scheduleCronTask(cronTask: CronTask)
}

data class CronTasks(var version: String, var updateTime: Long) {

}

class DefaultJobSchedule : JobSchedule {
    @Resource
    lateinit var objectMapper: ObjectMapper

    @Resource
    lateinit var executorService: ScheduledExecutorService
    private var map = HashMap<String, CronTasks>()
    private fun validVersion(app: String, profile: String, version: String, updateTime: Long): Boolean {
        val cronTasks = map[String.format("%s-%s", app, profile)]
        return cronTasks?.version.equals(version) && cronTasks?.updateTime?.equals(updateTime) ?: false
    }

    private fun executeTask(
        app: String,
        profile: String,
        version: String,
        updateTime: Long,
        plan: TaskType,
        executor: TaskExecutor
    ) {
        executorService.schedule({
            if (plan.repeatExecutable()) {
                if (validVersion(app, profile, version, updateTime)) {
                    executeTask(app, profile, version, updateTime, plan, executor)
                } else {
                    return@schedule
                }
            }
            try {
                val localTime = LocalTime.now()
                println("start execute")
                val pair = executor.execute()
                println("time:{},pair:{}".format(localTime.format(DateTimeFormatter.ISO_LOCAL_TIME), pair.toString()))
            } catch (e: Exception) {
                println(e.message)
            }

        }, plan.calLatestExecuteTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    }

    override fun scheduleOneTimeTask(oneTimeTask: OneTimeTask) {
        val urlTaskExecutor = objectMapper.readValue<UrlTaskExecutor>(oneTimeTask.execute, UrlTaskExecutor::class.java)
        oneTimeTask.run {

            executeTask(
                app, profile, id.toString(), updateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli(),
                OneTimeTaskType(
                    planTime.toEpochSecond(ZoneOffset.ofHours(-8)), subject, associateId
                ),
                urlTaskExecutor
            )
        }
    }

    override fun scheduleCronTask(cronTask: CronTask) {
        val urlTaskExecutor = objectMapper.readValue<UrlTaskExecutor>(cronTask.execute, UrlTaskExecutor::class.java)
        cronTask.run {
            val time = updateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli()
            map.put(String.format("%s-%s", app, profile), CronTasks(version.toString(), time))
            executeTask(
                app,
                profile,
                version.toString(),
                time,
                CronTaskType(cronTask.cron),
                urlTaskExecutor
            )
        }

    }


}
