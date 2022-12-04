package com.youfun.task.core.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import org.springframework.scheduling.support.CronExpression
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * @author jackmiking
 * @date 2022/11/2
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
sealed class TaskType(val type: TaskPlanTypeEnum = TaskPlanTypeEnum.ONE_TIME) {
    /**
     * milliSecond
     */
    abstract fun calLatestExecuteTime(): Long

    /**
     * whether allow to excute more than once
     */
    abstract fun repeatExecutable(): Boolean
}

enum class TaskPlanTypeEnum {
    CRON, ONE_TIME;
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
@JsonTypeName("CRON")
data class CronTaskType(val cron: String) : TaskType(TaskPlanTypeEnum.CRON) {
    constructor():this("")
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
@JsonTypeName("ONE_TIME")
data class OneTimeTaskType(val planTime: Long=System.currentTimeMillis(),
                           //only for record.make your task associate with this value.
                           val subject:String="",
                           //only for record. a more accurate value to associate with.
                           val subjectId:String="") : TaskType(TaskPlanTypeEnum.ONE_TIME) {
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