package com.youfun.task.core.dto

import org.springframework.scheduling.support.CronExpression
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * @author jackmiking
 * @date 2022/11/2
 */

sealed class TaskTypeDetail(val type: TaskPlanType = TaskPlanType.oneTime) {
    /**
     * milliSecond
     */
    abstract fun calLatestExecuteTime(): Long

    /**
     * whether allow to excute more than once
     */
    abstract fun repeatExecutable(): Boolean
}

enum class TaskPlanType {
    cron, oneTime;
}

/**
 * only get time in multiExecutable
 */
fun TaskTypeDetail.nextExecuteTime(): Long? {
    if (repeatExecutable()) {
        return calLatestExecuteTime()
    } else {
        return null
    }
}

/**
 * @param cron (second minute hour day-of-month month day-of-week)
 */
data class CronTaskType(val cron: String) : TaskTypeDetail(TaskPlanType.cron) {
    override fun calLatestExecuteTime(): Long {
        val item = CronExpression.parse(cron)
        val next: LocalDateTime? = item.next(LocalDateTime.now())
        return next!!.toInstant(ZoneOffset.of("+8")).toEpochMilli()
    }

    override fun repeatExecutable(): Boolean = true
}

/**
 * @param planTime : when to execute this task.run only once.
 */
data class OneTimeTaskType(val planTime: Long) : TaskTypeDetail(TaskPlanType.oneTime) {
    override fun calLatestExecuteTime(): Long {
        return planTime
    }

    override fun repeatExecutable(): Boolean = false
}

fun main() {
    val cron = CronTaskType("0 0/10 * * * *")
    val long = cron.calLatestExecuteTime()
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(long), ZoneId.of("+8"))
    println(dateTime)
}