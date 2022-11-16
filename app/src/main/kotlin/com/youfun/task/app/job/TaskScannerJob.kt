package com.youfun.task.app.job

import com.youfun.task.app.entity.CronTask
import com.youfun.task.app.entity.OneTimeTaskStatus
import com.youfun.task.app.repository.CronTaskRepository
import com.youfun.task.app.repository.OneTimeTaskRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@Service
class TaskScannerJob {
    @Resource
    lateinit var cronTaskRepository: CronTaskRepository;

    @Resource
    lateinit var oneTimeTaskRepository: OneTimeTaskRepository;
    var localDateTime = LocalDateTime.MIN
    var min = 0L
    fun scanCronTask() {
        var cronTaskList: List<CronTask> = cronTaskRepository.findByUpdateTimeAfter(localDateTime);
        cronTaskList.forEach {

        }

    }

    fun scanOneTimeTask() {
        var oneTimeTaskList = oneTimeTaskRepository.findByPlanTimeBetweenAndStatus(
            min, System.currentTimeMillis() + TimeUnit.SECONDS.toNanos(10), OneTimeTaskStatus.`init`.name
        )
    }
}