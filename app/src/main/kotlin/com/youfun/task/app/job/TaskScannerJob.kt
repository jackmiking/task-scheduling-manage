package com.youfun.task.app.job

import com.youfun.task.app.entity.CronTask
import com.youfun.task.app.entity.CronTaskStatus
import com.youfun.task.app.entity.OneTimeTaskStatus
import com.youfun.task.app.repository.CronTaskRepository
import com.youfun.task.app.repository.OneTimeTaskRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@Service
class TaskScannerJob {
    @Resource
    lateinit var cronTaskRepository: CronTaskRepository;

    @Resource
    lateinit var oneTimeTaskRepository: OneTimeTaskRepository;

    @Resource
    lateinit var jobSchedule: JobSchedule
    private var cronTaskMin: Date = Date(0)
    private var oneTimeTaskMin = 0L

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    fun scanCronTask() {
        val cronTaskList: List<CronTask> =
            cronTaskRepository.findByStatusAndUpdateTimeAfter(CronTaskStatus.SCHEDULING.name, cronTaskMin);
        cronTaskList.forEach(jobSchedule::scheduleCronTask)
        cronTaskMin = cronTaskList.maxByOrNull { it.updateTime }?.updateTime ?: cronTaskMin
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    fun scanOneTimeTask() {
        val oneTimeTaskList = oneTimeTaskRepository.findByPlanTimeBetweenAndStatus(
            Date(oneTimeTaskMin), Date(System.currentTimeMillis() + TimeUnit.SECONDS.toNanos(10)), OneTimeTaskStatus.INIT.name
        )
        oneTimeTaskMin = oneTimeTaskList.map { it.planTime.time }.maxOrNull() ?: oneTimeTaskMin
        oneTimeTaskMin+=1
        oneTimeTaskList.forEach(jobSchedule::scheduleOneTimeTask)

    }
}