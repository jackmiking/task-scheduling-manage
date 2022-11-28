package com.youfun.task.app.job

import com.youfun.task.app.entity.CronTask
import com.youfun.task.app.entity.CronTaskStatus
import com.youfun.task.app.entity.OneTimeTaskStatus
import com.youfun.task.app.repository.CronTaskRepository
import com.youfun.task.app.repository.OneTimeTaskRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
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
    private var localDateTime: LocalDateTime = LocalDateTime.MIN
    private var min = 0L

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    fun scanCronTask() {
        val cronTaskList: List<CronTask> = cronTaskRepository.findByStatusAndUpdateTimeAfter(CronTaskStatus.SCHEDULING.name,localDateTime);
        cronTaskList.forEach(jobSchedule::scheduleCronTask)
        localDateTime=cronTaskList.maxByOrNull { it.updateTime }?.updateTime!!
    }

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    fun scanOneTimeTask() {
        val oneTimeTaskList = oneTimeTaskRepository.findByPlanTimeBetweenAndStatus(
            min, System.currentTimeMillis() + TimeUnit.SECONDS.toNanos(10), OneTimeTaskStatus.`init`.name
        )
        min=oneTimeTaskList.map { it.planTime.toEpochSecond(ZoneOffset.ofHours(-8)) }.maxOrNull()!!
        oneTimeTaskList.forEach(jobSchedule::scheduleOneTimeTask)

    }
}