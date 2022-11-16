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

sealed class TaskType(val type: TaskPlanType = TaskPlanType.oneTime) {
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
fun TaskType.nextExecuteTime(): Long? {
    if (repeatExecutable()) {
        return calLatestExecuteTime()
    } else {
        return null
    }
}

/**
 * @param cron (second minute hour day-of-month month day-of-week)
 */
data class CronTaskType(val cron: String) : TaskType(TaskPlanType.cron) {
    override fun calLatestExecuteTime(): Long {
        val item = CronExpression.parse(cron)
        val next: LocalDateTime? = item.next(LocalDateTime.now())
        return next!!.toInstant(ZoneOffset.of("+8")).toEpochMilli()
    }

    override fun repeatExecutable(): Boolean = true
}

/**
 * @param planTime : when to execute this task.run only once.
 * for example: you may have a task to modify a content table with an id 3.
 * then subject can be content,associativeId can be 3.
 *
 */
data class OneTimeTaskType(val planTime: Long,
                           //only for record.make your task associate with this value.
                           val subject:String="",
                           //only for record. a more accurate value to associate with.
                           val associativeId:String="") : TaskType(TaskPlanType.oneTime) {
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