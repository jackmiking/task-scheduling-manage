package com.youfun.task.app.job

import com.fasterxml.jackson.databind.ObjectMapper
import com.youfun.task.app.entity.CronTask
import com.youfun.task.app.entity.OneTimeTask
import com.youfun.task.core.dto.*
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

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

    val logger = LoggerFactory.getLogger("executeTask")
    private fun executeTask(
        app: String,
        profile: String,
        version: String,
        name: String,
        updateTime: Long,
        plan: TaskType,
        executor: TaskExecutor
    ) {
        executorService.schedule({
            if (plan.repeatExecutable()) {
                if (validVersion(app, profile, version, updateTime)) {
                    executeTask(app, profile, version, name, updateTime, plan, executor)
                } else {
                    logger.info(
                        "task/deprecated:{},{},{},{},{} is out of version.",
                        app,
                        profile,
                        version,
                        name,
                        updateTime
                    )
                    return@schedule
                }
            }
            try {
                val result = executor.execute(name)
                logger.info(
                    "task/executed:{},{},{},{},{},result[code:{},message:{}]", name,
                    app,
                    profile,
                    version, executor,
                    result.first,
                    result.second
                )
            } catch (e: Exception) {
                logger.error(e.message)
            }

        }, plan.calLatestExecuteTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
    }

    override fun scheduleOneTimeTask(oneTimeTask: OneTimeTask) {
        val urlTaskExecutor = objectMapper.readValue<UrlTaskExecutor>(oneTimeTask.execute, UrlTaskExecutor::class.java)
        oneTimeTask.run {
            executeTask(
                app, profile, id.toString(), oneTimeTask.name, updateTime.time,
                OneTimeTaskType(
                    planTime.time, subject, subjectId
                ),
                urlTaskExecutor
            )
        }
    }

    override fun scheduleCronTask(cronTask: CronTask) {
        val urlTaskExecutor = objectMapper.readValue<UrlTaskExecutor>(cronTask.execute, UrlTaskExecutor::class.java)

        cronTask.run {
            val time = updateTime.time
            map.put(String.format("%s-%s", app, profile), CronTasks(version.toString(), time))
            executeTask(
                app,
                profile,
                version.toString(), cronTask.name,
                time,
                CronTaskType(cronTask.cron),
                urlTaskExecutor
            )
        }

    }


}
